package de.coronavirus.imis.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.domain.Laboratory;
import de.coronavirus.imis.domain.LaboratoryNotFoundException;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientEvent;
import de.coronavirus.imis.domain.PatientNotFoundException;
import de.coronavirus.imis.domain.TestStatus;
import de.coronavirus.imis.repositories.LabTestRepository;
import de.coronavirus.imis.repositories.LaboratoryRepository;
import de.coronavirus.imis.repositories.PatientEventRepository;

@Component
@RequiredArgsConstructor
public class LabTestService {
    private final PatientService patientService;
    private final PatientEventService eventService;
    private final LaboratoryRepository laboratoryRepository;
    private final LabTestRepository labTestRepository;
    private final PatientEventRepository eventRepository;


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

    @Transactional
    public PatientEvent updateLapStatus(String testId, final String statusString) {
        TestStatus statusToSet = TestStatus.valueOf(statusString.toUpperCase());
        var labTest = labTestRepository.findById(testId).orElseThrow();
        final List<PatientEvent> event = eventService.getForLabTest(labTest);
        labTest.setTestStatus(statusToSet);
        var eventType = testStatusToEvent(statusToSet);
        var patient = event.stream().map(PatientEvent::getPatient).findFirst().orElse(null);
        var doctor = event.stream().map(PatientEvent::getResponsibleDoctor).findFirst().orElse(null);
        var changeEvent = PatientEvent.builder().eventType(eventType).labTest(labTest)
                .responsibleDoctor(doctor)
                .patient(patient)
                .build();
        return eventRepository.save(changeEvent);
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
