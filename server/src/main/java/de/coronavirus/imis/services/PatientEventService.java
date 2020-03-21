package de.coronavirus.imis.services;

import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.Illness;
import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientEvent;
import de.coronavirus.imis.repositories.PatientEventRepository;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PatientEventService {

    private PatientEventRepository patientEventRepository;

    public PatientEventService(PatientEventRepository patientEventRepository) {
        this.patientEventRepository = patientEventRepository;
    }

    public void createInitialPatientEvent(Patient patient, Optional<Illness> illness,
                                          EventType eventType) {
        var concreteIllness = illness.orElse(Illness.CORONA);
        PatientEvent event = new PatientEvent()
                .setEventTimestamp(Timestamp.from(Instant.now()))
                .setEventType(eventType)
                .setIllness(concreteIllness)
                .setPatient(patient);
        patientEventRepository.save(event);
    }

    public void createLabTestEvent(Patient patient, LabTest labTest, Optional<Illness> illness) {
        var concreteIllness = illness.orElse(Illness.CORONA);
        PatientEvent event = new PatientEvent()
                .setEventTimestamp(Timestamp.from(Instant.now()))
                .setEventType(EventType.TEST_SUBMITTED)
                .setLabTest(labTest)
                .setIllness(concreteIllness)
                .setPatient(patient);
        patientEventRepository.saveAndFlush(event);
    }

    public List<PatientEvent> getAllForPatient(Patient patient) {
        return patientEventRepository.findAllByPatient(patient);
    }


}
