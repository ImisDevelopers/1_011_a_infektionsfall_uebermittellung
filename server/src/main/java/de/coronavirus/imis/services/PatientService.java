package de.coronavirus.imis.services;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;

import de.coronavirus.imis.api.CreatePatientDTO;
import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.Illness;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.repositories.PatientRepository;

@Service
public class PatientService {
    private PatientRepository patientRepository;
    private PatientEventService eventService;

    private static Illness corona = Illness.builder().displayName("corona").build();

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Iterable<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> findPatientById(String id) {
        return patientRepository.findById(id);
    }

    @Transactional
    public Patient addPatient(final CreatePatientDTO dto) {
        var dateParsed = LocalDateTime.parse(dto.getBirthDate(), DateTimeFormatter.ISO_INSTANT).toLocalDate();
        var mappedPatient = Patient.builder().city(dto.getCity()).email(dto.getEmail())
                .firstName(dto.getFirstName()).lastName(dto.getLastName()).gender(dto.getGender())
                .houseNumber(dto.getHouseNumber()).street(dto.getStreet()).zip(dto.getZip())
                .insuranceCompany(dto.getInsuranceCompany())
                .dateOfBirth(dateParsed)
                .insuranceMembershipNumber(dto.getInsuranceMembershipNumber()).build();
        var id = Hashing.sha256().hashString(mappedPatient.getFirstName() + mappedPatient.getLastName() + mappedPatient.getZip()
                + mappedPatient.getDateOfBirth(), StandardCharsets.UTF_8).toString();
        mappedPatient.setId(id);
        patientRepository.save(mappedPatient);
        eventService.createInitialPatientEvent(mappedPatient, corona, EventType.SUSPECTED);
        return mappedPatient;
    }
}
