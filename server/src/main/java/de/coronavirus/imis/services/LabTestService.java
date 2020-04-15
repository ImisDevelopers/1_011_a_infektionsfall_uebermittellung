package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.CreateLabTestDTO;
import de.coronavirus.imis.api.dto.UpdateTestStatusDTO;
import de.coronavirus.imis.domain.*;
import de.coronavirus.imis.mapper.LabTestMapper;
import de.coronavirus.imis.repositories.LabTestEventRepository;
import de.coronavirus.imis.repositories.LabTestRepository;
import de.coronavirus.imis.repositories.LaboratoryRepository;
import de.coronavirus.imis.repositories.PatientEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
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
	private final PatientEventRepository patientEventRepository;
	private final LabTestEventRepository labTestEventRepository;

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

		labTestRepository.save(labTest);

		var labTestEvent = new LabTestEvent();
		labTestEvent.setEventTimestamp(OffsetDateTime.now());
		labTestEvent.setLabTest(labTest);
		labTestEvent.setLabTestEventType(dto.getStatus());
		labTestEvent.setComment(dto.getComment());
		labTestEventRepository.save(labTestEvent);

		return labTest;
	}

}
