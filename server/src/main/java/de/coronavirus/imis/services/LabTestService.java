package de.coronavirus.imis.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.domain.Laboratory;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientNotFoundException;
import de.coronavirus.imis.domain.TestStatus;
import de.coronavirus.imis.repositories.LaboratoryRepository;
import de.coronavirus.imis.repositories.PatientEventRepository;

@Service
@RequiredArgsConstructor
public class LabTestService {
    private final PatientEventRepository eventRepository;
    private final PatientService patientService;
    private final LaboratoryRepository laboratoryRepository;


    public LabTest createLabTest(String patientId, Long labId) {
        final Patient patient = patientService.findPatientById(patientId).orElseThrow(PatientNotFoundException::new);
        final Laboratory laboratory = laboratoryRepository.findById(labId).orElseThrow(PatientNotFoundException::new);
        final LabTest labTest = LabTest.builder().laboratory(laboratory).testStatus(TestStatus.TEST_SUBMITTED).build();
        return null;
    }

}
