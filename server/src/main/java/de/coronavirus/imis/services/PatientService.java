package de.coronavirus.imis.services;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.coronavirus.imis.api.dto.PatientSearchParamsDTO;
import de.coronavirus.imis.services.util.LikeOperatorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;

import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.repositories.PatientRepository;

@Service
@Slf4j
@AllArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientEventService eventService;
    private final LikeOperatorService likeOperatorService;


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
                .hashString(dto.getFirstName() + dto.getLastName() + dto.getZip() + dateParsed, StandardCharsets.UTF_8)
                .toString()
                .substring(0, 8).toUpperCase();
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
                .setZip(dto.getZip())
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

    public List<Patient> queryPatients(PatientSearchParamsDTO patientSearchParamsDTO) {
        final Sort sortBy = Sort.by(Sort.Direction.fromOptionalString(patientSearchParamsDTO.getOrder()).orElse(Sort.Direction.ASC), patientSearchParamsDTO.getOrderBy());

        final Pageable pageable = PageRequest.of(patientSearchParamsDTO.getOffsetPage().intValue(), patientSearchParamsDTO.getPageSize().intValue(), sortBy);

        final var patients = patientRepository.findAllByPatientSearchParams(
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getFirstName()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getLastName()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getId()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getGender()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getEmail()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getPhoneNumber()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getStreet()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getHouseNumber()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getZip()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getCity()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getInsuranceCompany()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getInsuranceMembershipNumber()),
                patientSearchParamsDTO.isConfirmed(),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getDoctorId()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getLaboratoryId()),
                pageable);
        if (patientSearchParamsDTO.isIncludePatientEvents()) {
            return patients.stream().peek(patient -> {
                var lastEvent = eventService.findFirstByPatientOrderByEventTimestampDesc(patient);
                patient.setEvents(List.of(lastEvent));
            }).collect(Collectors.toList());
        } else {
            return patients;
        }
    }

    public Long countQueryPatients(PatientSearchParamsDTO patientSearchParamsDTO) {
        return patientRepository.countPatientSearchParams(
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getFirstName()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getLastName()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getId()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getGender()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getEmail()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getPhoneNumber()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getStreet()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getHouseNumber()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getZip()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getCity()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getInsuranceCompany()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getInsuranceMembershipNumber()),
                patientSearchParamsDTO.isConfirmed(),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getDoctorId()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getLaboratoryId()));
    }
}
