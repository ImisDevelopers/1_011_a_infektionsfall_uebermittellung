package de.coronavirus.imis.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.domain.Laboratory;
import de.coronavirus.imis.domain.LaboratoryNotFoundException;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientEvent;
import de.coronavirus.imis.domain.PatientNotFoundException;
import de.coronavirus.imis.domain.TestStatus;
import de.coronavirus.imis.repositories.LabTestRepository;
import de.coronavirus.imis.repositories.LaboratoryRepository;

@Component
@RequiredArgsConstructor
public class LabTestService {
    private final PatientService patientService;
    private final PatientEventService eventService;
    private final LaboratoryRepository laboratoryRepository;
    private final LabTestRepository labTestRepository;


    @Transactional
    public LabTest createLabTest(String patientId, Long labId, String labInternalId) {
        final Patient patient = patientService.findPatientById(patientId).orElseThrow(PatientNotFoundException::new);
        final Laboratory laboratory = laboratoryRepository.findById(labId).orElseThrow(LaboratoryNotFoundException::new);
        final LabTest labTest = LabTest.builder().
                laboratory(laboratory).testStatus(TestStatus.TEST_SUBMITTED)
                .laborTestID(labInternalId)
                .build();
        labTestRepository.save(labTest);
        eventService.createLabTestEvent(patient, labTest, Optional.empty());
        return labTest;
    }

    @Transactional
    public Set<LabTest> getAllLabTestForPatient(String patiendId) {
        final Patient patient = patientService.findPatientById(patiendId).orElseThrow(PatientNotFoundException::new);
        final var events = eventService.getAllForPatient(patient);
        return events.stream().map(PatientEvent::getLabTest).collect(Collectors.toSet());
    }

}
