package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.CreateLabTestDTO;
import de.coronavirus.imis.api.dto.UpdateTestStatusDTO;
import de.coronavirus.imis.domain.*;
import de.coronavirus.imis.mapper.LabTestMapper;
import de.coronavirus.imis.repositories.LabTestRepository;
import de.coronavirus.imis.repositories.LaboratoryRepository;
import de.coronavirus.imis.repositories.PatientEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class LabTestService {

	private final PatientService patientService;
	private final PatientEventService eventService;
	private final LaboratoryRepository laboratoryRepository;
	private final LabTestRepository labTestRepository;
	private final PatientEventRepository eventRepository;

	private final LabTestMapper labTestMapper;

	@Transactional
	public LabTest createLabTest(Patient patient, LabTest labTest) {
		labTestRepository.save(labTest);
		eventService.createLabTestEvent(patient, labTest, Optional.empty());
		return labTest;
	}

	@Transactional
	public LabTest createLabTest(CreateLabTestDTO dto) {
		final Patient patient = patientService
				.findPatientById(dto.getPatientId())
				.orElseThrow(PatientNotFoundException::new);

		var labTest = labTestMapper.toLabTest(dto);
		return createLabTest(patient, labTest);
	}

	@Transactional
	public Set<LabTest> getAllLabsTestForPatient(String patiendId) {
		final Patient patient = patientService.findPatientById(patiendId).orElseThrow(PatientNotFoundException::new);
		final var events = eventService.getAllForPatient(patient);
		return events.stream().map(PatientEvent::getLabTest)
				.filter(Objects::nonNull).collect(Collectors.toSet());
	}

	@Transactional
	public List<LabTest> queryLabTestsById(String labTestId) {
		return this.labTestRepository.findByTestIdContaining(labTestId);
	}

	@Transactional
	public LabTest updateTestStatus(final String laboratoryId, final UpdateTestStatusDTO dto) {
		var labTest = labTestRepository
				.findFirstByTestIdAndLaboratoryId(dto.getTestId(), laboratoryId)
				.orElseThrow();

		labTest.setTestStatus(dto.getStatus());
		labTest.setReport(dto.getFile());
		labTest.setLastUpdate(OffsetDateTime.now());

		final List<PatientEvent> event = eventService.getForLabTest(labTest);
		var eventType = testStatusToEvent(dto.getStatus());

		var patient = event.stream()
				.map(PatientEvent::getPatient)
				.findFirst()
				.orElseThrow();

		patient.setPatientStatus(eventType);
		patientService.updatePatient(patient);

		var changeEvent = new PatientEvent()
				.setIllness(Illness.CORONA)
				.setEventType(eventType)
				.setEventTimestamp(Timestamp.valueOf(LocalDateTime.now()))
				.setLabTest(labTest)
				.setPatient(patient)
				.setComment(dto.getComment());

		event.stream()
				.map(PatientEvent::getResponsibleDoctor)
				.filter(Objects::nonNull)
				.findFirst()
				.ifPresent(changeEvent::setResponsibleDoctor);

		eventRepository.save(changeEvent);

		return labTest;
	}

	private EventType testStatusToEvent(TestStatus input) {
		EventType result;
		switch (input) {
			case TEST_NEGATIVE:
				result = EventType.TEST_FINISHED_NEGATIVE;
				break;
			case TEST_SUBMITTED:
			case TEST_IN_PROGRESS:
				result = EventType.TEST_SUBMITTED_IN_PROGRESS;
				break;
			case TEST_POSITIVE:
				result = EventType.TEST_FINISHED_POSITIVE;
				break;
			default:
				result = EventType.TEST_FINISHED_INVALID;

		}
		return result;
	}

}
