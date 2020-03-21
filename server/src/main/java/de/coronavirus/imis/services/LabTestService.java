package de.coronavirus.imis.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.domain.Laboratory;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientNotFoundException;
import de.coronavirus.imis.domain.TestStatus;
import de.coronavirus.imis.repositories.LabTestRepository;
import de.coronavirus.imis.repositories.LaboratoryRepository;

@Service
@RequiredArgsConstructor
public class LabTestService {
    private final PatientService patientService;
    private final PatientEventService eventService;
    private final LaboratoryRepository laboratoryRepository;
    private final LabTestRepository labTestRepository;

    @Transactional
    public LabTest createLabTest(String patientId, Long labId) {
        final Patient patient = patientService.findPatientById(patientId).orElseThrow(PatientNotFoundException::new);
        final Laboratory laboratory = laboratoryRepository.findById(labId).orElseThrow(PatientNotFoundException::new);
        final LabTest labTest = LabTest.builder().laboratory(laboratory).testStatus(TestStatus.TEST_SUBMITTED).build();
        eventService.createLabTestEvent(patient, labTest, Optional.empty());
        return labTestRepository.save(labTest);
    }

}
