package de.coronavirus.imis.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.Illness;
import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientEvent;
import de.coronavirus.imis.repositories.PatientEventRepository;

@Service
@RequiredArgsConstructor
public class PatientEventService {

    private static Illness corona = Illness.builder().displayName("corona").build();


    private PatientEventRepository patientEventRepository;

    public void createInitialPatientEvent(Patient patient, Optional<Illness> illness,
            EventType eventType) {
        var concreteIllnes = illness.orElse(corona);
        PatientEvent event = PatientEvent.builder().eventTimestamp(Timestamp.from(Instant.now()))
                .eventType(eventType)
                .illness(concreteIllnes)
                .patient(patient).build();
        patientEventRepository.save(event);
    }

    public void createLabTestEvent(Patient patient, LabTest labTest, Optional<Illness> illness) {
        var concreteIllnes = illness.orElse(corona);
        PatientEvent event = PatientEvent.builder().eventTimestamp(Timestamp.from(Instant.now()))
                .eventType(EventType.TEST_SUBMITTED)
                .labTest(labTest)
                .illness(concreteIllnes)
                .patient(patient).build();
        patientEventRepository.saveAndFlush(event);
    }


}
