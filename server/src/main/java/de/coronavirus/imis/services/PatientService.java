package de.coronavirus.imis.services;

import com.google.common.hash.Hashing;
import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.api.dto.PatientSearchParamsDTO;
import de.coronavirus.imis.api.dto.PatientSimpleSearchParamsDTO;
import de.coronavirus.imis.api.dto.SendToQuarantineDTO;
import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.mapper.PatientMapper;
import de.coronavirus.imis.repositories.PatientRepository;
import de.coronavirus.imis.services.util.LikeOperatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PatientService {

	private final PatientRepository patientRepository;
	private final PatientEventService eventService;
	private final LikeOperatorService likeOperatorService;
	private final RandomService randomService;
	private final PatientMapper patientMapper;
	private final IncidentService incidentService;
	@PersistenceContext
	private final EntityManager sessionFactory;


	public List<Patient> getAllPatients() {
		var patients = patientRepository.findAll();
		return patients.stream().map(patient -> {
			var lastEvent = eventService.findFirstByPatientOrderByEventTimestampDesc(patient);
			patient.setEvents(List.of(lastEvent));
			return patient;
		}).collect(Collectors.toList());
	}

	public Optional<Patient> findPatientById(String id) {
		return patientRepository.findById(id);
	}

	public Patient addPatient(CreatePatientDTO dto, boolean registeredByInstitution) {
		var patient = patientMapper.toPatient(dto);
		if (registeredByInstitution) {
			patient.setPatientStatus(EventType.SUSPECTED);
		}
		return this.addPatient(
				patient,
				patientMapper.parseDate(dto.getDateOfReporting()));
	}

	public Patient updatePatient(Patient patient) {
		return this.patientRepository.saveAndFlush(patient);
	}

	public Patient addPatient(Patient patient, final LocalDate dateOfReporting) {
		if (patient.getId() == null) {
			var id = Hashing.sha256()
					.hashString(patient.getFirstName() + patient.getLastName()
							+ patient.getZip()
							+ patient.getDateOfBirth()
							+ randomService.getRandomString(12), StandardCharsets.UTF_8)
					.toString()
					.substring(0, 8).toUpperCase();

			patient.setId(id);
		}

		patient = patientRepository.save(patient);
		log.info("inserting patient with id {}", patient.getId());
		eventService.createInitialPatientEvent(
				patient, Optional.empty(),
				patient.getPatientStatus(),
				dateOfReporting);
		log.info("inserted event for patient {}", patient);
		incidentService.addIncident(
				patient, Optional.empty(),
				patient.getPatientStatus(),
				dateOfReporting);
		return patient;
	}

	public Long queryPatientsSimpleCount(String query) {
		return this.patientRepository.count(getSimpleQuerySpecification(query));
	}

	public List<Patient> queryPatientsSimple(PatientSimpleSearchParamsDTO query) {
		final Sort sortBy = getSort(query.getOrder(), query.getOrderBy());
		final Pageable pageable = getPageable(query.getOffsetPage(), query.getPageSize(), sortBy);
		return this.patientRepository.findAll(getSimpleQuerySpecification(query.getQuery()), pageable).toList();
	}

	private Sort getSort(String order, String orderBy) {
		return Sort.by(Sort.Direction.fromOptionalString(order).orElse(Sort.Direction.ASC), orderBy);
	}

	private Pageable getPageable(Long offsetPage, Long pageSize, Sort sortBy) {
		return PageRequest.of(offsetPage.intValue(), pageSize.intValue(), sortBy);
	}

	private Specification<Patient> getSimpleQuerySpecification(String query) {
		// each part of the query has to be in any of these attributes:
		final List<String> attributes = Arrays.asList("firstName", "lastName", "id", "email", "phoneNumber", "city");
		// "any" means we have to concatenate with "or"
		final List<Specification<Patient>> specifications = Arrays.stream(query.split(" "))
				.map(queryPart -> Specification.<Patient>where((root, q, criteriaBuilder) ->
						criteriaBuilder.or(
								attributes.stream()
										.map(attribute -> like(root, criteriaBuilder, attribute, queryPart))
										.toArray(Predicate[]::new)
						)
				)).collect(Collectors.toList());

		// Each Specification contains the filter for a single attribute
		// These have to be joined together with "and"
		return specifications.stream().skip(1)
				.reduce(specifications.get(0), Specification::and);
	}

	private Predicate like(Root<Patient> root, CriteriaBuilder criteriaBuilder, String propertyName, String query) {
		return criteriaBuilder.like(criteriaBuilder.lower(root.get(propertyName)), "%" + query.toLowerCase() + "%");
	}

	public List<Patient> queryPatients(PatientSearchParamsDTO patientSearchParamsDTO) {
		final String orderBy;
		if (patientSearchParamsDTO.getOrderBy() == null || patientSearchParamsDTO.getOrderBy().isEmpty()) {
			orderBy = "id";
		} else {
			orderBy = patientSearchParamsDTO.getOrderBy();
		}
		final String sortOrder = patientSearchParamsDTO.getOrder() != null && !patientSearchParamsDTO.getOrder().isEmpty()
				? patientSearchParamsDTO.getOrder() : "ASC";

		final String sql =
				"select distinct pat from Patient pat "
						+ this.getQueryPatientsSql(patientSearchParamsDTO)
						+ " order by pat." + orderBy + " " + sortOrder;
		final TypedQuery<Patient> query = this.getQuery(sql, patientSearchParamsDTO, Patient.class);
		query.setFirstResult(patientSearchParamsDTO.getOffsetPage().intValue());
		query.setMaxResults(patientSearchParamsDTO.getPageSize().intValue());
		final List<Patient> patients = query.getResultList();

		if (patientSearchParamsDTO.isIncludePatientEvents()) {
			patients.forEach(patient -> {
				var lastEvent = eventService.findFirstByPatientOrderByEventTimestampDesc(patient);
				patient.setEvents(List.of(lastEvent));
			});
		}
		return patients;
	}

	private String nvl(String text) {
		return text == null ? "" : text;
	}

	public Long countQueryPatients(PatientSearchParamsDTO patientSearchParamsDTO) {
		String sql = "select count(distinct pat) from Patient pat " + getQueryPatientsSql(patientSearchParamsDTO);
		return this.getQuery(sql, patientSearchParamsDTO, Long.class).getSingleResult();
	}

	private <T> TypedQuery<T> getQuery(String sql, PatientSearchParamsDTO patientSearchParamsDTO, Class<T> returnClazz) {
		final TypedQuery<T> query = this.sessionFactory.createQuery(sql, returnClazz);
		query.setParameter(1, likeOperatorService.like(patientSearchParamsDTO.getFirstName()));
		query.setParameter(2, likeOperatorService.like(patientSearchParamsDTO.getLastName()));
		query.setParameter(3, likeOperatorService.like(patientSearchParamsDTO.getId()));
		query.setParameter(4, likeOperatorService.like(patientSearchParamsDTO.getGender()));
		query.setParameter(5, likeOperatorService.like(patientSearchParamsDTO.getEmail()));
		query.setParameter(6, likeOperatorService.like(patientSearchParamsDTO.getPhoneNumber()));
		query.setParameter(7, likeOperatorService.like(patientSearchParamsDTO.getStreet()));
		query.setParameter(8, likeOperatorService.like(patientSearchParamsDTO.getHouseNumber()));
		query.setParameter(9, likeOperatorService.like(patientSearchParamsDTO.getZip()));
		query.setParameter(10, likeOperatorService.like(patientSearchParamsDTO.getCity()));
		query.setParameter(11, likeOperatorService.like(patientSearchParamsDTO.getInsuranceCompany()));
		query.setParameter(12, likeOperatorService.like(patientSearchParamsDTO.getInsuranceMembershipNumber()));
		query.setParameter(13, likeOperatorService.like(patientSearchParamsDTO.getDoctorId()));
		query.setParameter(14, likeOperatorService.like(patientSearchParamsDTO.getLaboratoryId()));
		query.setParameter(15, likeOperatorService.like(patientSearchParamsDTO.getPatientStatus() == null ? "" : patientSearchParamsDTO.getPatientStatus().name()));
		int positionParameter = 16;
		if (patientSearchParamsDTO.getQuarantineStatus() != null && !patientSearchParamsDTO.getQuarantineStatus().isEmpty()) {
			for (EventType quarantineStatus : patientSearchParamsDTO.getQuarantineStatus()) {
				if (quarantineStatus != null) {
					query.setParameter(positionParameter++, quarantineStatus.name());
				}
			}
		}
		return query;
	}

	private String getQueryPatientsSql(PatientSearchParamsDTO patientSearchParamsDTO) {
		StringBuilder sql = new StringBuilder(
						"left join PatientEvent pe on pe.patient = pat.id " +
						"left join QuarantineIncident qi on qi.patient = pat " +
						"where lower(pat.firstName) like lower(?1) " +
						"AND lower(pat.lastName) like lower(?2) " +
						"AND lower(pat.id) like lower(?3) " +
						"AND lower(pat.gender) like lower(?4) " +
						"AND lower(pat.email) like lower(?5) " +
						"AND lower(pat.phoneNumber) like lower(?6) " +
						"AND lower(pat.street) like lower(?7) " +
						"AND lower(pat.houseNumber) like lower(?8) " +
						"AND lower(pat.zip) like lower(?9) " +
						"AND lower(pat.city) like lower(?10) " +
						"AND lower(pat.insuranceCompany) like lower(?11) " +
						"AND lower(pat.insuranceMembershipNumber) like lower(?12) " +
						"AND lower(coalesce(pe.responsibleDoctor, '0')) like lower(?13) " +
						"AND lower(coalesce(pe.labTest, '0')) like lower(?14) " +
						"AND lower(pat.patientStatus) like lower(?15) "
		);
		int positionSql = 16;
		if (patientSearchParamsDTO.getQuarantineStatus() != null && !patientSearchParamsDTO.getQuarantineStatus().isEmpty()) {
			sql.append("AND ( 1=0 ");
			for (EventType quarantineStatus : patientSearchParamsDTO.getQuarantineStatus()) {
				if (quarantineStatus != null) {
					sql.append("OR lower(qi.eventType) = lower(?").append(positionSql++).append(") ");
				} else {
					sql.append("OR qi.eventType is null ");
				}
			}
			sql.append(" ) ");
		}
		return sql.toString();
	}

	@Transactional
	public Patient sendToQuarantine(final String patientID, final SendToQuarantineDTO dto) {

		var patient = findPatientById(patientID).orElseThrow();

		patient.setQuarantineUntil(patientMapper.parseDate(dto.getDateUntil()));

		updatePatient(patient);

		eventService.createQuarantineEvent(patient, dto.getDateUntil(), dto.getComment());

		return patient;
	}
}
