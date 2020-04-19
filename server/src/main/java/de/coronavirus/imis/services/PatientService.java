package de.coronavirus.imis.services;

import com.google.common.hash.Hashing;
import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.api.dto.PatientSearchParamsDTO;
import de.coronavirus.imis.api.dto.PatientSimpleSearchParamsDTO;
import de.coronavirus.imis.domain.InstitutionType;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientEventType;
import de.coronavirus.imis.mapper.PatientInformationMapper;
import de.coronavirus.imis.repositories.PatientInformationRepository;
import de.coronavirus.imis.repositories.PatientRepository;
import de.coronavirus.imis.services.util.LikeOperatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
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
	private final AuthService authService;
	private final PatientInformationRepository patientInformationRepository;

	private final PatientInformationMapper patientInformationMapper;


	public List<Patient> getAllPatients() {
		// TODO: Will crash if we have too many patients - move filter to query
		var patients = patientRepository.findAll();
		return filterPatients(patients).stream().peek(patient -> {
			var lastEvent = eventService.findFirstByPatientOrderByEventTimestampDesc(patient);
			patient.setEvents(List.of(lastEvent));
		}).collect(Collectors.toList());
	}

	public Optional<Patient> findPatientById(String id) {
		return patientRepository.findById(id);
	}

	public Patient updatePatient(Patient patient) {
		return this.patientRepository.saveAndFlush(patient);
	}

	public Patient addPatient(CreatePatientDTO dto) {

		var patientInformation = patientInformationMapper.toPatient(dto);
		var patient = new Patient();
		patient.setPatientStatus(dto.getPatientStatus() == null ? PatientEventType.REGISTERED : dto.getPatientStatus());
		patient.setInformation(Collections.singletonList(patientInformation));
		var id = Hashing.sha256()
				.hashString(patientInformation.getFirstName() + patientInformation.getLastName()
						+ patientInformation.getZip()
						+ patientInformation.getDateOfBirth()
						+ randomService.getRandomString(12), StandardCharsets.UTF_8)
				.toString()
				.substring(0, 8).toUpperCase();
		patient.setId(id);
		patient = patientRepository.save(patient);

		patientInformation.setPatient(patient);
		patientInformation.setUser(authService.getCurrentUser());
		patientInformationRepository.save(patientInformation);



		log.info("inserting patient with id {}", patient.getId());
		eventService.createPatientEvent(
				patient,
				patient.getPatientStatus(),
				dto.getEventTimestamp(),
				null
		);
		log.info("inserted event for patient {}", patient);
		return patient;
	}

	public Long queryPatientsSimpleCount(String query) {
		// TODO: Move auth filter logic to query to fix count
		return this.patientRepository.count(getSimpleQuerySpecification(query));
	}

	public List<Patient> queryPatientsSimple(PatientSimpleSearchParamsDTO query) {
		final Sort sortBy = getSort(query.getOrder(), query.getOrderBy());
		final Pageable pageable = getPageable(query.getOffsetPage(), query.getPageSize(), sortBy);
		return filterPatients(this.patientRepository.findAll(getSimpleQuerySpecification(query.getQuery()), pageable).toList());
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

	public List<Patient> queryPatients(PatientSearchParamsDTO patientSearchParamsDTO) {
		final String orderBy;
		if (patientSearchParamsDTO.getOrderBy() == null || patientSearchParamsDTO.getOrderBy().isEmpty()) {
			orderBy = "id";
		} else {
			orderBy = patientSearchParamsDTO.getOrderBy();
		}
		final Sort sortBy = Sort.by(Sort.Direction.fromOptionalString(patientSearchParamsDTO.getOrder()).orElse(Sort.Direction.ASC), orderBy);

		final Pageable pageable = PageRequest.of(patientSearchParamsDTO.getOffsetPage().intValue(), patientSearchParamsDTO.getPageSize().intValue(), sortBy);

		final var patients = patientRepository.findAllByPatientSearchParams(
				likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getFirstName())),
				likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getLastName())),
				likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getId())),
				likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getGender())),
				likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getEmail())),
				likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getPhoneNumber())),
				likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getStreet())),
				likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getHouseNumber())),
				likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getZip())),
				likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getCity())),
				likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getInsuranceCompany())),
				likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getInsuranceMembershipNumber())),
//				likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getDoctorId())),
//				likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getLaboratoryId())),
				likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getPatientStatus() == null ? "" : patientSearchParamsDTO.getPatientStatus().name()),
				pageable);

		final List<Patient> patientsReturn;

		if (patientSearchParamsDTO.isIncludePatientEvents()) {
			patientsReturn = patients.stream().peek(patient -> {
				var lastEvent = eventService.findFirstByPatientOrderByEventTimestampDesc(patient);
				patient.setEvents(List.of(lastEvent));
			}).collect(Collectors.toList());
		} else {
			patientsReturn = patients;
		}
		return filterPatients(patientsReturn);
	}

	public Long countQueryPatients(PatientSearchParamsDTO patientSearchParamsDTO) {
		// TODO: Move auth filter logic to query to fix count
		return patientRepository.countPatientSearchParams(
				likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getFirstName()),
				likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getLastName()),
				likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getId()),
				likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getGender()),
				likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getEmail()),
				likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getPhoneNumber()),
				likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getStreet()),
				likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getHouseNumber()),
				likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getZip()),
				likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getCity()),
				likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getInsuranceCompany()),
				likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getInsuranceMembershipNumber()),
//				likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getDoctorId()),
//				likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getLaboratoryId()),
				likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getPatientStatus() == null ? "" : patientSearchParamsDTO.getPatientStatus().name()));
	}

	private Sort getSort(String order, String orderBy) {
		return Sort.by(Sort.Direction.fromOptionalString(order).orElse(Sort.Direction.ASC), orderBy);
	}

	private Pageable getPageable(Long offsetPage, Long pageSize, Sort sortBy) {
		return PageRequest.of(offsetPage.intValue(), pageSize.intValue(), sortBy);
	}

	private Predicate like(Root<Patient> root, CriteriaBuilder criteriaBuilder, String propertyName, String query) {
		return criteriaBuilder.like(criteriaBuilder.lower(root.get(propertyName)), "%" + query.toLowerCase() + "%");
	}

	private String nvl(String text) {
		return text == null ? "" : text;
	}

	private List<Patient> filterPatients(List<Patient> patients) {
		var currentInstitution = authService.getCurrentUser().institution();
		if (currentInstitution.getType() == InstitutionType.DEPARTMENT_OF_HEALTH) {
			return patients;
		}
		// For all except the department of health, we only want to show them the data they entered themselves:
		return patients.stream().peek(patient -> {
			patient.setInformation(
					patient.getInformation().stream()
							.filter(info -> info.getUser().getInstitutionId().equals(currentInstitution.getId()))
							.collect(Collectors.toList())
			);
		}).filter(patient -> patient.getInformation().size() == 0).collect(Collectors.toList());
	}

}
