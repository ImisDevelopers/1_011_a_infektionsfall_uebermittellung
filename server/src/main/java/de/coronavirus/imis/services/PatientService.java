package de.coronavirus.imis.services;

import com.google.common.hash.Hashing;
import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.api.dto.PatientSearchParamsDTO;
import de.coronavirus.imis.api.dto.PatientSimpleSearchParamsDTO;
import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.mapper.PatientMapper;
import de.coronavirus.imis.repositories.PatientRepository;
import de.coronavirus.imis.services.incidents.WriteIncidentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PatientService {

	private final PatientRepository patientRepository;
	private final PatientEventService eventService;
	private final PatientMapper patientMapper;
	private final WriteIncidentService incidentService;
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

		LocalDate dateOfReporting = dto.getDateOfReporting() != null ? PatientMapper.parseDate(dto.getDateOfReporting()) : LocalDate.now();
		LocalDate dateOfIllness = dto.getDateOfIllness() != null ? PatientMapper.parseDate(dto.getDateOfIllness()) : LocalDate.now();

		if (patient.getId() == null) {
			patient.setId(makePatientId(patient));
		}

		log.info("inserting patient with id {}", patient.getId());
		patient = patientRepository.save(patient);

		eventService.createInitialPatientEvent(
			patient, Optional.empty(),
			patient.getPatientStatus(),
			dateOfReporting);

		incidentService.addOrUpdateAdministrativeIncident(
			patient, Optional.empty(),
			patient.getPatientStatus(),
			dateOfReporting,
			dateOfIllness
		);

		return patient;
	}

	public Patient updatePatient(Patient patient) {
		incidentService.deductIncidentUpdates(patient);
		return patientRepository.saveAndFlush(patient);
	}

	public Long queryPatientsSimpleCount(String query) {
		return this.searchService.getResultSizePatientsSimple(query);
	}

	public List<Patient> queryPatientsSimple(PatientSimpleSearchParamsDTO query) {
		return this.searchService.queryPatientsSimple(query);
	}


	public List<Patient> queryPatients(PatientSearchParamsDTO patientSearchParamsDTO) {
		return searchService.queryPatientsDetail(patientSearchParamsDTO);
	}

	public Long countQueryPatients(PatientSearchParamsDTO patientSearchParamsDTO) {
		return this.searchService.getResultSizePatientsDetail(patientSearchParamsDTO);
	}

	@SuppressWarnings("UnstableApiUsage")
	private String makePatientId(Patient patient) {
		return Hashing.sha256()
			.hashString(patient.getFirstName() + patient.getLastName()
				+ patient.getZip()
				+ patient.getDateOfBirth()
				+ RandomService.getRandomString(12), StandardCharsets.UTF_8)
			.toString()
			.substring(0, 8).toUpperCase();
	}

}
