package de.coronavirus.imis.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import de.coronavirus.imis.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import de.coronavirus.imis.domain.Doctor;
import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.Illness;
import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.domain.Laboratory;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientEvent;
import de.coronavirus.imis.repositories.DoctorRepository;
import de.coronavirus.imis.repositories.LaboratoryRepository;
import de.coronavirus.imis.repositories.PatientEventRepository;

@Service
@AllArgsConstructor
public class PatientEventService {

    private final PatientEventRepository patientEventRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public void createInitialPatientEvent(Patient patient,
                                          Optional<Illness> illness,
                                          EventType eventType,
                                          LocalDate dateOfReporting) {
        var concreteIllness = illness.orElse(Illness.CORONA);
        patient.setPatientStatus(eventType);
        patientRepository.save(patient);
        final Timestamp eventTimestamp;
        if (dateOfReporting != null) {
           eventTimestamp = Timestamp.valueOf(dateOfReporting.atTime(12, 0));
        } else {
            eventTimestamp = Timestamp.from(Instant.now());
        }
        PatientEvent event = new PatientEvent()
                .setEventTimestamp(eventTimestamp)
                .setEventType(eventType)
                .setIllness(concreteIllness)
                .setPatient(patient);
        patientEventRepository.save(event);
    }

    public void createLabTestEvent(Patient patient, LabTest labTest,
                                   Optional<Illness> illness) {
        var concreteIllness = illness.orElse(Illness.CORONA);
        patient.setPatientStatus(EventType.TEST_SUBMITTED_IN_PROGRESS);
        patientRepository.save(patient);
        PatientEvent event = new PatientEvent()
                .setEventTimestamp(Timestamp.from(Instant.now()))
                .setEventType(EventType.TEST_SUBMITTED_IN_PROGRESS)
                .setLabTest(labTest)
                .setIllness(concreteIllness)
                .setPatient(patient);
        patientEventRepository.saveAndFlush(event);
    }

    @Transactional
    public PatientEvent createScheduledEvent(Patient patient, String labId, String doctorId) {
        final Laboratory laboratory = laboratoryRepository.findById(labId).orElseGet(() -> {
            Laboratory lab = new Laboratory();
            lab.setId(labId);
            return laboratoryRepository.save(lab);
        });
        final Doctor doctor = doctorRepository.findById(doctorId).orElseGet(() ->
                {
                    var newDoctor = new Doctor();
                    newDoctor.setId(doctorId);
                    return doctorRepository.save(newDoctor);
                }
        );
        patient.setPatientStatus(EventType.SCHEDULED_FOR_TESTING);
        patientRepository.save(patient);
        var event = new PatientEvent()
                .setEventTimestamp(Timestamp.from(Instant.now()))
                .setEventType(EventType.SCHEDULED_FOR_TESTING)
                .setIllness(Illness.CORONA)
                .setResponsibleDoctor(doctor)
                .setPatient(patient);
        return patientEventRepository.save(event);
    }

    public List<PatientEvent> getAllForPatient(Patient patient) {
        return patientEventRepository.findAllByPatient(patient);
    }

    public List<PatientEvent> getForLabTest(LabTest labTest) {
        return patientEventRepository.findPatientEventByLabTest(labTest);
    }


    public PatientEvent findFirstByPatientOrderByEventTimestampDesc(Patient patient) {
        return patientEventRepository.findFirstByPatientOrderByEventTimestampDesc(patient);
    }
}
