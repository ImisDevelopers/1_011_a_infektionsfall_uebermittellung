package de.coronavirus.imis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import de.coronavirus.imis.domain.*;
import de.coronavirus.imis.mapper.InstitutionMapper;
import de.coronavirus.imis.repositories.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.api.dto.CreateInstitutionDTO;
import de.coronavirus.imis.api.dto.RegisterUserRequest;


@Service
@RequiredArgsConstructor
public class InstitutionService {
    private final InstitutionRepository institutionRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;
    private final TestSiteRepository testSiteRepository;
    private final DepartmentOfHealthRepository departmentOfHealthRepository;

    private final InstitutionMapper institutionMapper;

    public List<Laboratory> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Transactional
    public <T extends InstitutionImpl> T addInstitution(T institution) {
        return (T) this.institutionRepository.saveAndFlush((InstitutionImpl) institution);
    }
    public InstitutionImpl addInstitution(CreateInstitutionDTO dto) {
        return this.addInstitution((InstitutionImpl) this.institutionMapper.toInstitution(dto));
    }

    public InstitutionImpl getInstitution(String id, InstitutionType type) {
        return institutionRepository.getOne(id);
    }

    /**
     * Query for institution by part of id
     * @param id id or a part of an id
     * @return List of matching institutions
     */
    @Transactional
    public List<Laboratory> queryLaboratory(String id) {
        return laboratoryRepository.queryLaboratory(id);
    }
}
