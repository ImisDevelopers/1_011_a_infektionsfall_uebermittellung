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

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;

import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.RiskOccupation;
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
        var occupation = determineRiskOcc(dto.getRiskOccupation());
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
                .setPatientStatus(EventType.SUSPECTED)
                .setCity(dto.getCity())
                .setInsuranceCompany(dto.getInsuranceCompany())
                .setInsuranceMembershipNumber(dto.getInsuranceMembershipNumber())
                .setFluImmunization(dto.getFluImmunization())
                .setSpeedOfSymptomsOutbreak(dto.getSpeedOfSymptomsOutbreak())
                .setSymptoms(dto.getSymptoms())
                .setCoronaContacts(dto.getCoronaContacts())
                .setRiskAreas(dto.getRiskAreas())
                .setWeakenedImmuneSystem(dto.getWeakenedImmuneSystem())
                .setRiskOccupation(occupation)
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


    public List<Patient> queryPatientsSimple(String query) {
        return this.patientRepository.findAllByPatientQuery(query);
    }

    public List<Patient> queryPatients(PatientSearchParamsDTO patientSearchParamsDTO) {
        final Sort sortBy = Sort.by(Sort.Direction.fromOptionalString(patientSearchParamsDTO.getOrder()).orElse(Sort.Direction.ASC), patientSearchParamsDTO.getOrderBy());

        final Pageable pageable = PageRequest.of(patientSearchParamsDTO.getOffsetPage().intValue(), patientSearchParamsDTO.getPageSize().intValue(), sortBy);

        final var patients = patientRepository.findAllByPatientSearchParams(
                likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getFirstName())),
                likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getLastName())),
                likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getId())),
                likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getGender())),
                likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getEmail())),
                likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getPhoneNumber())),
                likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getStreet())),
                likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getHouseNumber())),
                likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getZip())),
                likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getCity())),
                likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getInsuranceCompany())),
                likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getInsuranceMembershipNumber())),
                likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getDoctorId())),
                likeOperatorService.likeOperatorOrEmptyString(nvl(patientSearchParamsDTO.getLaboratoryId())),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getPatientStatus() == null ? "" : patientSearchParamsDTO.getPatientStatus().name()),
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

    private String nvl(String text) {
        return text == null ? "" : text;
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
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getDoctorId()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getLaboratoryId()),
                likeOperatorService.likeOperatorOrEmptyString(patientSearchParamsDTO.getPatientStatus() == null ? "" : patientSearchParamsDTO.getPatientStatus().name()));
    }

    private RiskOccupation determineRiskOcc(String occ) {
        if (Strings.isNullOrEmpty(occ)) {
            return RiskOccupation.NO_RISK_OCCUPATION;
        }
        try {
            return RiskOccupation.valueOf(occ);
        } catch (Exception e) {
            log.error("error determining risk occupation for {}", occ);
        }
        return RiskOccupation.NO_RISK_OCCUPATION;
    }
}
