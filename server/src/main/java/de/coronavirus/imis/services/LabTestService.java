package de.coronavirus.imis.services;

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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LabTestService {

    private final PatientService patientService;
    private final PatientEventService eventService;
    private final LaboratoryRepository laboratoryRepository;
    private final LabTestRepository labTestRepository;
    private final PatientEventRepository eventRepository;

    @Transactional
    public LabTest createLabTest(String patientId, String laboratoryId, String testId, String comment) {
        final Patient patient = patientService
                .findPatientById(patientId)
                .orElseThrow(PatientNotFoundException::new);
        final Laboratory laboratory = laboratoryRepository
                .findById(laboratoryId)
                .orElseThrow(LaboratoryNotFoundException::new);
        final LabTest labTest = LabTest.builder()
                .laboratory(laboratory)
                .testStatus(TestStatus.TEST_SUBMITTED)
                .testId(testId)
                .comment(comment)
                .build();

        labTestRepository.save(labTest);
        eventService.createLabTestEvent(patient, labTest, Optional.empty());
        return labTest;
    }

    @Transactional
    public Set<LabTest> getAllLabsTestForPatient(String patiendId) {
        final Patient patient = patientService.findPatientById(patiendId).orElseThrow(PatientNotFoundException::new);
        final var events = eventService.getAllForPatient(patient);
        return events.stream().map(PatientEvent::getLabTest).collect(Collectors.toSet());
    }

    @Transactional
    public PatientEvent updateTestStatus(String testId, final String statusString) {
        TestStatus statusToSet = TestStatus.valueOf(statusString.toUpperCase());
        var labTest = labTestRepository.findById(testId).orElseThrow();
        final List<PatientEvent> event = eventService.getForLabTest(labTest);
        labTest.setTestStatus(statusToSet);
        var eventType = testStatusToEvent(statusToSet);
        var patient = event.stream().map(PatientEvent::getPatient).findFirst().orElseThrow();
        var doctor = event.stream().map(PatientEvent::getResponsibleDoctor)
                .filter(Objects::nonNull).findFirst();

        var changeEvent = new PatientEvent()
                .setEventType(eventType)
                .setLabTest(labTest)
                .setPatient(patient);
        doctor.ifPresent(changeEvent::setResponsibleDoctor);
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
