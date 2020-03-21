package de.coronavirus.imis.services;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.Illness;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientEvent;
import de.coronavirus.imis.repositories.PatientEventRepository;

@Service
@RequiredArgsConstructor
public class PatientEventService {

    private PatientEventRepository patientEventRepository;

    public void createInitialPatientEvent(Patient patient, Illness illness, EventType eventType) {
        PatientEvent event = PatientEvent.builder().eventTimestamp(Timestamp.from(Instant.now()))
                .eventType(eventType)
                .illness(illness).patient(patient).build();
        patientEventRepository.save(event);
    }

}
