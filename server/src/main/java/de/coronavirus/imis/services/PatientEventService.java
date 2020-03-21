package de.coronavirus.imis.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.domain.Doctor;
import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.Illness;
import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.domain.Laboratory;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientEvent;
import de.coronavirus.imis.domain.PatientNotFoundException;
import de.coronavirus.imis.repositories.DoctorRepository;
import de.coronavirus.imis.repositories.LaboratoryRepository;
import de.coronavirus.imis.repositories.PatientEventRepository;

@Service
@RequiredArgsConstructor
public class PatientEventService {

    private static Illness corona = Illness.builder().displayName("corona").build();


    private final PatientEventRepository patientEventRepository;
    private final PatientService patientRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final DoctorRepository doctorRepository;

    public void createInitialPatientEvent(Patient patient, Optional<Illness> illness,
            EventType eventType) {
        PatientEvent event = PatientEvent.builder().eventTimestamp(Timestamp.from(Instant.now()))
                .eventType(eventType)
                .illness("corona")
                .patient(patient)
                .build();
        patientEventRepository.save(event);
    }

    public void createLabTestEvent(Patient patient, LabTest labTest, Optional<Illness> illness) {
        PatientEvent event = PatientEvent.builder().eventTimestamp(Timestamp.from(Instant.now()))
                .eventType(EventType.TEST_SUBMITTED)
                .labTest(labTest)
                .illness("corona")
                .patient(patient).build();
        patientEventRepository.saveAndFlush(event);
    }

    @Transactional
    public PatientEvent createScheduledEvent(String patientId, String labId, String doctorId) {
        final Patient patient = patientRepository.findPatientById(patientId).orElseThrow(PatientNotFoundException::new);
        final Laboratory laboratory = laboratoryRepository.findById(Long.valueOf(labId)).orElseGet(() -> {
            Laboratory lab = new Laboratory();
            lab.setId(Long.valueOf(labId));
            return laboratoryRepository.save(lab);
        });


        final Doctor doctor = doctorRepository.findById(doctorId).orElseGet(() ->
                doctorRepository.save(Doctor.builder().id(Long.valueOf(doctorId)).build())
        );
        var event = PatientEvent.builder().eventTimestamp(Timestamp.from(Instant.now()))
                .eventType(EventType.SCHEDULED_FOR_TESTING)
                .responsibleDoctor(doctor)
                .patient(patient)
                .build();
        return patientEventRepository.save(event);

    }

    public List<PatientEvent> getAllForPatient(Patient patient) {
        return patientEventRepository.findAllByPatient(patient);
    }

    public List<PatientEvent> getForLabTest(LabTest labTest) {
        return patientEventRepository.findPatientEventByLabTest(labTest);
    }


}
