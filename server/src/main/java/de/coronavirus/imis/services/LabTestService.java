package de.coronavirus.imis.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.domain.Laboratory;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientNotFoundException;
import de.coronavirus.imis.domain.TestStatus;
import de.coronavirus.imis.repositories.LabTestRepository;
import de.coronavirus.imis.repositories.LaboratoryRepository;

@Component
public class LabTestService {
    private final PatientService patientService;
    private final PatientEventService eventService;
    private final LaboratoryRepository laboratoryRepository;
    private final LabTestRepository labTestRepository;

    @Autowired
    public LabTestService(final PatientService patientService, final PatientEventService eventService, final LaboratoryRepository laboratoryRepository, final LabTestRepository labTestRepository) {
        this.patientService = patientService;
        this.eventService = eventService;
        this.laboratoryRepository = laboratoryRepository;
        this.labTestRepository = labTestRepository;
    }

    @Transactional
    public LabTest createLabTest(String patientId, Long labId, String labInternalId) {
        final Patient patient = patientService.findPatientById(patientId).orElseThrow(PatientNotFoundException::new);
        final Laboratory laboratory = laboratoryRepository.findById(labId).orElseThrow(PatientNotFoundException::new);
        final LabTest labTest = LabTest.builder().
                laboratory(laboratory).testStatus(TestStatus.TEST_SUBMITTED)
                .laborTestID(labInternalId)
                .build();
        eventService.createLabTestEvent(patient, labTest, Optional.empty());
        return labTestRepository.save(labTest);
    }

}
