package de.coronavirus.imis.services;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.repositories.PatientRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientEventService eventService;


    public Optional<Patient> findPatientById(String id) {
        return patientRepository.findById(id);
    }

    @Transactional
    public Patient addPatient(final CreatePatientDTO dto) {
        var dateParsed = parseDateSafe(dto.getBirthDate());
        var id = Hashing.sha256().hashString(dto.getFirstName() + dto.getLastName() + dto.getZip()
                + dateParsed, StandardCharsets.UTF_8).toString();
        var mappedPatient = Patient.builder().city(dto.getCity()).email(dto.getEmail())
                .firstName(dto.getFirstName()).lastName(dto.getLastName()).gender(dto.getGender())
                .houseNumber(dto.getHouseNumber()).street(dto.getStreet()).zip(dto.getZip())
                .insuranceCompany(dto.getInsuranceCompany())
                .dateOfBirth(dateParsed)
                .insuranceMembershipNumber(dto.getInsuranceMembershipNumber())
                .id(id)
                .build();
        patientRepository.saveAndFlush(mappedPatient);
        eventService.createInitialPatientEvent(mappedPatient, Optional.empty(), EventType.SUSPECTED);
        return mappedPatient;
    }

    private LocalDate parseDateSafe(String date) {
        try {
            return LocalDateTime.parse(date, DateTimeFormatter.ISO_INSTANT).toLocalDate();
        } catch (Exception e) {
            log.error("error parsing date", e);
        }
        return null;
    }

}
