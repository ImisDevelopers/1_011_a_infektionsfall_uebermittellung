package de.coronavirus.imis.services;

import de.coronavirus.imis.domain.*;
import de.coronavirus.imis.repositories.LabTestRepository;
import de.coronavirus.imis.repositories.LaboratoryRepository;
import de.coronavirus.imis.repositories.PatientEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    public List<LabTest> queryLabTestsById(String labTestId) {
        return this.labTestRepository.findByTestIdContaining(labTestId);
    }

    @Transactional
    public LabTest updateTestStatus(final String testId, final String laboratoryId
            , final String statusString, final String comment, final byte[] file) {
        TestStatus statusToSet = TestStatus.valueOf(statusString.toUpperCase());

        var labTest = labTestRepository.findFirstByTestIdAndLaboratoryId(testId,
                laboratoryId).orElseThrow();

        labTest.setTestStatus(statusToSet);
        labTest.setReport(file);

        final List<PatientEvent> event = eventService.getForLabTest(labTest);
        var eventType = testStatusToEvent(statusToSet);

        var patient = event.stream()
                .map(PatientEvent::getPatient)
                .findFirst()
                .orElseThrow();

        var changeEvent = new PatientEvent()
                .setIllness(Illness.CORONA)
                .setEventType(eventType)
                .setEventTimestamp(Timestamp.valueOf(LocalDateTime.now()))
                .setLabTest(labTest)
                .setPatient(patient)
                .setComment(comment);

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
