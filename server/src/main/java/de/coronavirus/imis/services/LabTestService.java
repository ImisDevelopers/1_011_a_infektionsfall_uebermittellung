package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.CreateLabTestDTO;
import de.coronavirus.imis.api.dto.UpdateTestStatusDTO;
import de.coronavirus.imis.domain.*;
import de.coronavirus.imis.mapper.LabTestMapper;
import de.coronavirus.imis.repositories.LabTestRepository;
import de.coronavirus.imis.repositories.PatientCaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class LabTestService {

	private final PatientService patientService;
	private final PatientEventService eventService;
	private final LabTestRepository labTestRepository;
	private final LabTestEventService labTestEventService;
	private final PatientCaseRepository patientCaseRepository;

	private final LabTestMapper labTestMapper;

	@Transactional
	public LabTest createLabTest(CreateLabTestDTO dto) {

		var labTest = labTestMapper.toLabTest(dto);
		final LabTest createdLabTest = labTestRepository.save(labTest);

		this.labTestEventService.createLabTestEvent(
				labTest,
				LabTestEventType.TEST_SUBMITTED_IN_PROGRESS,
				dto.getEventTimestamp() != null ? dto.getEventTimestamp() : OffsetDateTime.now(),
				dto.getComment()
		);

		return createdLabTest;
	}

	@Transactional
	public List<LabTest> getAllLabsTestForPatient(String patientId) {
		final Patient patient = patientService.findPatientById(patientId).orElseThrow(PatientNotFoundException::new);
		return patient.getCases().stream()
				.flatMap(patientCase -> patientCase.getLabTests().stream())
				.collect(Collectors.toList());
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
		labTestEventService.createLabTestEvent(labTest, dto.getStatus(), OffsetDateTime.now(), dto.getComment());

		return labTest;
	}

}
