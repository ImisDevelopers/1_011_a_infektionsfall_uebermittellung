package de.coronavirus.imis.services;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;

import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.repositories.PatientRepository;

@Service
@Slf4j
public class PatientService {

    private PatientRepository patientRepository;
    private PatientEventService eventService;

    public PatientService(PatientRepository patientRepository, PatientEventService eventService) {
        this.patientRepository = patientRepository;
        this.eventService = eventService;
    }

    public List<Patient> getAllPatients() {
        var patients = patientRepository.findAll();
        return patients.stream().map(patient -> {
            var lastEvent = eventService.findFirstByPatientOrderByEventTimestampDesc(patient);
            patient.setEvents(List.of(lastEvent));
            return patient;
        }).collect(Collectors.toList());
    }

    public Optional<Patient> findPatientById(String id) {
        return patientRepository.findById(id);
    }

    public Patient addPatient(final CreatePatientDTO dto) {
        var dateParsed = LocalDateTime.parse(dto.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH")).toLocalDate();
        var id = Hashing.sha256()
                .hashString(dto.getFirstName() + dto.getLastName() + dto.getPostalCode() + dateParsed, StandardCharsets.UTF_8)
                .toString();
        var mappedPatient = new Patient()
                .setId(id)
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setGender(dto.getGender())
                .setDateOfBirth(dateParsed)
                .setEmail(dto.getEmail())
                .setPhoneNumber(dto.getPhoneNumber())
                .setStreet(dto.getStreet())
                .setHouseNumber(dto.getHouseNumber())
                .setCity(dto.getCity())
                .setHouseNumber(dto.getHouseNumber())
                .setStreet(dto.getStreet())
                .setZip(Integer.valueOf(dto.getPostalCode()))
                .setCity(dto.getCity())
                .setInsuranceCompany(dto.getInsuranceCompany())
                .setInsuranceMembershipNumber(dto.getInsuranceMembershipNumber())
                .setFluImmunization(dto.getFluImmunization())
                .setSpeedOfSymptomsOutbreak(dto.getSpeedOfSymptomsOutbreak())
                .setSymptoms(dto.getSymptoms())
                .setCoronaContacts(dto.getCoronaContacts())
                .setRiskAreas(dto.getRiskAreas())
                .setWeakenedImmuneSystem(dto.getWeakenedImmuneSystem())
                .setPreIllnesses(dto.getPreIllnesses());
        mappedPatient = patientRepository.save(mappedPatient);
        log.info("inserting patient with id {}", mappedPatient.getId());
        eventService.createInitialPatientEvent(mappedPatient, Optional.empty(), EventType.SUSPECTED);
        log.info("inserted event for patient {}", mappedPatient);
        return mappedPatient;
    }

    private Integer parseIntegerSafe(String toParse) {
        try {
            return Integer.parseInt(toParse);
        } catch (Exception e) {
            log.error("error parsing integer");
        }
        return Integer.MIN_VALUE;
    }
}
