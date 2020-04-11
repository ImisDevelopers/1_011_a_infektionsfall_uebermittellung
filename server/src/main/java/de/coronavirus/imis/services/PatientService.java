package de.coronavirus.imis.services;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.api.dto.PatientSearchParamsDTO;
import de.coronavirus.imis.api.dto.PatientSimpleSearchParamsDTO;
import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.RiskOccupation;
import de.coronavirus.imis.repositories.PatientRepository;
import de.coronavirus.imis.services.util.LikeOperatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientEventService eventService;
    private final LikeOperatorService likeOperatorService;
    private final RandomService randomService;


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
        var dateOfBirthParsed = LocalDate.parse(dto.getDateOfBirth(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate dateOfDeathParsed = null;
        if (dto.getDateOfDeath() != null && !dto.getDateOfDeath().isBlank()) {
            dateOfDeathParsed = LocalDate.parse(dto.getDateOfDeath(), DateTimeFormatter.ISO_LOCAL_DATE);
        }
        var id = Hashing.sha256()
                .hashString(dto.getFirstName() + dto.getLastName() + dto.getZip() + dateOfBirthParsed + randomService.getRandomString(12), StandardCharsets.UTF_8)
                .toString()
                .substring(0, 8).toUpperCase();
        var mappedPatient = new Patient()
                // Basics
                .setId(id)
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setGender(dto.getGender())
                .setDateOfBirth(dateOfBirthParsed)
                .setDateOfDeath(dateOfDeathParsed)
                .setEmail(dto.getEmail())
                .setPhoneNumber(dto.getPhoneNumber())
                // Address
                .setStreet(dto.getStreet())
                .setHouseNumber(dto.getHouseNumber())
                .setCity(dto.getCity())
                .setZip(dto.getZip())
                // Stay
                .setStayStreet(dto.getStayStreet())
                .setStayHouseNumber(dto.getStayHouseNumber())
                .setStayCity(dto.getStayCity())
                .setStayZip(dto.getStayZip())
                // Other
                .setEmployer(dto.getEmployer())
                .setPatientStatus(EventType.SUSPECTED)
                .setInsuranceCompany(dto.getInsuranceCompany())
                .setInsuranceMembershipNumber(dto.getInsuranceMembershipNumber())
                .setFluImmunization(dto.getFluImmunization())
                .setSpeedOfSymptomsOutbreak(dto.getSpeedOfSymptomsOutbreak())
                .setSymptoms(dto.getSymptoms())
                .setCoronaContacts(dto.getCoronaContacts())
                .setRiskAreas(dto.getRiskAreas())
                .setWeakenedImmuneSystem(dto.getWeakenedImmuneSystem())
                .setRiskOccupation(dto.getRiskOccupation())
                .setOccupation(dto.getOccupation())
                .setCreationTimestamp(OffsetDateTime.now())
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

    public Long queryPatientsSimpleCount(String query) {
        return this.patientRepository.count(getSimpleQuerySpecification(query));
    }

    public List<Patient> queryPatientsSimple(PatientSimpleSearchParamsDTO query) {
        final Sort sortBy = getSort(query.getOrder(), query.getOrderBy());
        final Pageable pageable = getPageable(query.getOffsetPage(), query.getPageSize(), sortBy);
        return this.patientRepository.findAll(getSimpleQuerySpecification(query.getQuery()), pageable).toList();
    }

    private Sort getSort(String order, String orderBy) {
        return Sort.by(Sort.Direction.fromOptionalString(order).orElse(Sort.Direction.ASC), orderBy);
    }

    private Pageable getPageable(Long offsetPage, Long pageSize, Sort sortBy) {
        return PageRequest.of(offsetPage.intValue(), pageSize.intValue(), sortBy);
    }

    private Specification<Patient> getSimpleQuerySpecification(String query) {
        // each part of the query has to be in any of these attributes:
        final List<String> attributes = Arrays.asList("firstName", "lastName", "id", "email", "phoneNumber", "city");
        // "any" means we have to concatenate with "or"
        final List<Specification<Patient>> specifications = Arrays.stream(query.split(" "))
                .map(queryPart -> Specification.<Patient>where((root, q, criteriaBuilder) ->
                        criteriaBuilder.or(
                                attributes.stream()
                                        .map(attribute -> like(root, criteriaBuilder, attribute, queryPart))
                                        .toArray(Predicate[]::new)
                        )
                )).collect(Collectors.toList());

        // Each Specification contains the filter for a single attribute
        // These have to be joined together with "and"
        return specifications.stream().skip(1)
                .reduce(specifications.get(0), Specification::and);
    }

    private Predicate like(Root<Patient> root, CriteriaBuilder criteriaBuilder, String propertyName, String query) {
        return criteriaBuilder.like(criteriaBuilder.lower(root.get(propertyName)), "%" + query.toLowerCase() + "%");
    }

    public List<Patient> queryPatients(PatientSearchParamsDTO patientSearchParamsDTO) {
        final String orderBy;
        if (patientSearchParamsDTO.getOrderBy() == null || patientSearchParamsDTO.getOrderBy().isEmpty()) {
            orderBy = "id";
        } else {
            orderBy = patientSearchParamsDTO.getOrderBy();
        }
        final Sort sortBy = Sort.by(Sort.Direction.fromOptionalString(patientSearchParamsDTO.getOrder()).orElse(Sort.Direction.ASC), orderBy);

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

}
