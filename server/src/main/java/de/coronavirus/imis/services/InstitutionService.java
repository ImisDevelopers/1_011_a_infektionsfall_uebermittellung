package de.coronavirus.imis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import de.coronavirus.imis.api.dto.InstitutionDTO;
import de.coronavirus.imis.api.exception.InstitutionNotFoundException;
import de.coronavirus.imis.domain.*;
import de.coronavirus.imis.repositories.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.api.dto.CreateInstitutionDTO;
import de.coronavirus.imis.api.dto.RegisterUserRequest;


@Service
@RequiredArgsConstructor
public class InstitutionService {
    private final LaboratoryRepository laboratoryRepository;
    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;
    private final TestSiteRepository testSiteRepository;
    private final DepartmentOfHealthRepository departmentOfHealthRepository;

    public List<Laboratory> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Transactional
    public Institution updateInstitution(InstitutionDTO institutionDTO) {

        final var institution = getInstitution(institutionDTO.getId(), institutionDTO.getInstitutionType());
        institution.setCity(institutionDTO.getCity());
        institution.setComment(institutionDTO.getComment());
        institution.setEmail(institutionDTO.getEmail());
        institution.setHouseNumber(institutionDTO.getHouseNumber());
        institution.setName(institutionDTO.getName());
        institution.setPhoneNumber(institutionDTO.getPhoneNumber());
        institution.setStreet(institutionDTO.getStreet());
        institution.setZip(institutionDTO.getZip());
        switch (institution.getType()) {
            case DEPARTMENT_OF_HEALTH:
                departmentOfHealthRepository.save((DepartmentOfHealth) institution);
                break;
            case GOVERNMENT_AGENCY:
                // TODO: Government Agency?
                break;
            case DOCTORS_OFFICE:
                doctorRepository.save((Doctor) institution);
                break;
            case LABORATORY:
                laboratoryRepository.save((Laboratory) institution);
                break;
            case TEST_SITE:
                testSiteRepository.save((TestSite) institution);
                break;
            case CLINIC:
                clinicRepository.save((Clinic) institution);
                break;
        }
        return institution;
    }

    @Transactional
    public Doctor createDoctorInstitution(CreateInstitutionDTO institutionDTO) {
        var doctor = new Doctor();
        doctor.setName(institutionDTO.getName());
        doctor.setStreet(institutionDTO.getStreet());
        doctor.setHouseNumber(institutionDTO.getHouseNumber());
        doctor.setZip(institutionDTO.getZip());
        doctor.setCity(institutionDTO.getCity());
        doctor.setEmail(institutionDTO.getEmail());
        doctor.setPhoneNumber(institutionDTO.getPhoneNumber());
        doctor.setComment(institutionDTO.getComment());
        doctor.setId(UUID.randomUUID().toString());
        return this.doctorRepository.saveAndFlush(doctor);
    }

    @Transactional
    public Laboratory createLaboratoryInstitution(CreateInstitutionDTO institutionDTO) {
        var laboratory = new Laboratory();
        laboratory.setName(institutionDTO.getName());
        laboratory.setStreet(institutionDTO.getStreet());
        laboratory.setHouseNumber(institutionDTO.getHouseNumber());
        laboratory.setZip(institutionDTO.getZip());
        laboratory.setCity(institutionDTO.getCity());
        laboratory.setEmail(institutionDTO.getEmail());
        laboratory.setPhoneNumber(institutionDTO.getPhoneNumber());
        laboratory.setComment(institutionDTO.getComment());
        laboratory.setId(UUID.randomUUID().toString());
        return this.laboratoryRepository.saveAndFlush(laboratory);
    }

    @Transactional
    public DepartmentOfHealth createDepartmentOfHealthInstitution(CreateInstitutionDTO institutionDTO) {
        var departmentOfHealth = new DepartmentOfHealth();
        departmentOfHealth.setName(institutionDTO.getName());
        departmentOfHealth.setStreet(institutionDTO.getStreet());
        departmentOfHealth.setHouseNumber(institutionDTO.getHouseNumber());
        departmentOfHealth.setZip(institutionDTO.getZip());
        departmentOfHealth.setCity(institutionDTO.getCity());
        departmentOfHealth.setEmail(institutionDTO.getEmail());
        departmentOfHealth.setPhoneNumber(institutionDTO.getPhoneNumber());
        departmentOfHealth.setComment(institutionDTO.getComment());
        departmentOfHealth.setId(UUID.randomUUID().toString());

        return this.departmentOfHealthRepository.saveAndFlush(departmentOfHealth);
    }

    @Transactional
    public Clinic createClinicInstitution(CreateInstitutionDTO institutionDTO) {
        var clinic = new Clinic();
        clinic.setName(institutionDTO.getName());
        clinic.setStreet(institutionDTO.getStreet());
        clinic.setHouseNumber(institutionDTO.getHouseNumber());
        clinic.setZip(institutionDTO.getZip());
        clinic.setCity(institutionDTO.getCity());
        clinic.setEmail(institutionDTO.getEmail());
        clinic.setPhoneNumber(institutionDTO.getPhoneNumber());
        clinic.setComment(institutionDTO.getComment());
        clinic.setId(UUID.randomUUID().toString());

        return this.clinicRepository.saveAndFlush(clinic);
    }

    @Transactional
    public TestSite createTestSiteInstitution(CreateInstitutionDTO institutionDTO) {
        var testSite = new TestSite();
        testSite.setName(institutionDTO.getName());
        testSite.setStreet(institutionDTO.getStreet());
        testSite.setHouseNumber(institutionDTO.getHouseNumber());
        testSite.setZip(institutionDTO.getZip());
        testSite.setCity(institutionDTO.getCity());
        testSite.setEmail(institutionDTO.getEmail());
        testSite.setPhoneNumber(institutionDTO.getPhoneNumber());
        testSite.setComment(institutionDTO.getComment());
        testSite.setId(UUID.randomUUID().toString());

        return this.testSiteRepository.saveAndFlush(testSite);
    }

    public InstitutionDTO mapInstitution(Institution institution) {
        final InstitutionType type = institution.getType();
        switch (type) {
            case DEPARTMENT_OF_HEALTH:
                final DepartmentOfHealth departmentOfHealth = (DepartmentOfHealth) institution;
                return InstitutionDTO.builder()
                        .id(departmentOfHealth.getId())
                        .institutionType(type)
                        .name(departmentOfHealth.getName())
                        .street(departmentOfHealth.getStreet())
                        .houseNumber(departmentOfHealth.getHouseNumber())
                        .zip(departmentOfHealth.getZip())
                        .city(departmentOfHealth.getCity())
                        .email(departmentOfHealth.getEmail())
                        .phoneNumber(departmentOfHealth.getPhoneNumber())
                        .comment(departmentOfHealth.getComment())
                        .build();
            case LABORATORY:
                final Laboratory laboratory = (Laboratory) institution;
                return InstitutionDTO.builder()
                        .id(laboratory.getId())
                        .institutionType(type)
                        .name(laboratory.getName())
                        .street(laboratory.getStreet())
                        .houseNumber(laboratory.getHouseNumber())
                        .zip(laboratory.getZip())
                        .city(laboratory.getCity())
                        .email(laboratory.getEmail())
                        .phoneNumber(laboratory.getPhoneNumber())
                        .comment(laboratory.getComment())
                        .build();
            case TEST_SITE:
                final TestSite testSite = (TestSite) institution;
                return InstitutionDTO.builder()
                        .id(testSite.getId())
                        .institutionType(type)
                        .name(testSite.getName())
                        .street(testSite.getStreet())
                        .houseNumber(testSite.getHouseNumber())
                        .zip(testSite.getZip())
                        .city(testSite.getCity())
                        .email(testSite.getEmail())
                        .phoneNumber(testSite.getPhoneNumber())
                        .comment(testSite.getComment())
                        .build();
            case CLINIC:
                final Clinic clinic = (Clinic) institution;
                return InstitutionDTO.builder()
                        .id(clinic.getId())
                        .institutionType(type)
                        .name(clinic.getName())
                        .street(clinic.getStreet())
                        .houseNumber(clinic.getHouseNumber())
                        .zip(clinic.getZip())
                        .city(clinic.getCity())
                        .email(clinic.getEmail())
                        .phoneNumber(clinic.getPhoneNumber())
                        .comment(clinic.getComment())
                        .build();
            case DOCTORS_OFFICE:
                final Doctor doctor = (Doctor) institution;
                return InstitutionDTO.builder()
                        .id(doctor.getId())
                        .institutionType(type)
                        .name(doctor.getName())
                        .street(doctor.getStreet())
                        .houseNumber(doctor.getHouseNumber())
                        .zip(doctor.getZip())
                        .city(doctor.getCity())
                        .email(doctor.getEmail())
                        .phoneNumber(doctor.getPhoneNumber())
                        .comment(doctor.getComment())
                        .build();
        }
        throw new IllegalArgumentException("Unknown type " + type);
    }

    protected Institution createInstitution(CreateInstitutionDTO dto) {
        Institution result = null;
        switch (dto.getInstitutionType()) {
            case CLINIC:
                result = createClinicInstitution(dto);
                break;
            case TEST_SITE:
                result = createTestSiteInstitution(dto);
                break;
            case LABORATORY:
                result = createLaboratoryInstitution(dto);
                break;
            case DOCTORS_OFFICE:
                result = createDoctorInstitution(dto);
                break;
            case GOVERNMENT_AGENCY:
                break;
        }
        return result;
    }

    protected Institution getInstitution(String id, InstitutionType type) {
        Institution result = null;
        switch (type) {
            case CLINIC:
                result = clinicRepository.getOne(id);
                break;
            case TEST_SITE:
                result = testSiteRepository.getOne(id);
                break;
            case LABORATORY:
                result = laboratoryRepository.getOne(id);
                break;
            case DOCTORS_OFFICE:
                result = doctorRepository.getOne(id);
                break;
            case DEPARTMENT_OF_HEALTH:
                result = departmentOfHealthRepository.getOne(id);
            case GOVERNMENT_AGENCY:
                break;
        }
        return result;
    }

    /**
     * Query for institution by part of id
     *
     * @param id id or a part of an id
     * @return List of matching institutions
     */
    @Transactional
    public List<Laboratory> queryLaboratory(String id) {
        return laboratoryRepository.queryLaboratory(id);
    }
}
