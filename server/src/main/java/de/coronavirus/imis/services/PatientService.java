package de.coronavirus.imis.services;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.api.dto.PatientSearchParamsDTO;
import de.coronavirus.imis.api.dto.PatientSimpleSearchParamsDTO;
import de.coronavirus.imis.api.dto.RequestQuarantineDTO;
import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.mapper.PatientMapper;
import de.coronavirus.imis.repositories.PatientRepository;

@Service
@Slf4j
@AllArgsConstructor
public class PatientService {

	private final PatientRepository patientRepository;
	private final PatientEventService eventService;
	private final RandomService randomService;
	private final PatientMapper patientMapper;
	private final IncidentService incidentService;
	private final SearchService searchService;

	public List<Patient> getAllPatients() {
		var patients = patientRepository.findAll();
		return patients.stream().peek(patient -> {
			var lastEvent = eventService.findFirstByPatientOrderByEventTimestampDesc(patient);
			patient.setEvents(List.of(lastEvent));
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
		return this.searchService.getResultSizePatientsSimple(query);
	}

	public List<Patient> queryPatientsSimple(PatientSimpleSearchParamsDTO query) {
		final Sort sortBy = getSort(query.getOrder(), query.getOrderBy());
		final Pageable pageable = getPageable(query.getOffsetPage(), query.getPageSize(), sortBy);
		return this.searchService.queryPatientsSimple(query);
	}

	private Sort getSort(String order, String orderBy) {
		return Sort.by(Sort.Direction.fromOptionalString(order).orElse(Sort.Direction.ASC), orderBy);
	}

	private Pageable getPageable(Long offsetPage, Long pageSize, Sort sortBy) {
		return PageRequest.of(offsetPage.intValue(), pageSize.intValue(), sortBy);
	}

	public List<Patient> queryPatients(PatientSearchParamsDTO patientSearchParamsDTO) {
		return searchService.queryPatientsDetail(patientSearchParamsDTO);
	}

	public Long countQueryPatients(PatientSearchParamsDTO patientSearchParamsDTO) {
		return this.searchService.getResultSizePatientsDetail(patientSearchParamsDTO);
	}

	@Transactional
	public Patient sendToQuarantine(final String patientID, final RequestQuarantineDTO dto) {

		var patient = findPatientById(patientID).orElseThrow();

		patient.setQuarantineUntil(patientMapper.parseDate(dto.getDateUntil()));

		updatePatient(patient);

		eventService.createQuarantineEvent(patient, dto.getDateUntil(), dto.getComment());

		return patient;
	}
}
