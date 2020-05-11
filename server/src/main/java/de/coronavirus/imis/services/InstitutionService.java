package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.CreateInstitutionDTO;
import de.coronavirus.imis.api.dto.InstitutionDTO;
import de.coronavirus.imis.domain.Doctor;
import de.coronavirus.imis.domain.InstitutionImpl;
import de.coronavirus.imis.domain.InstitutionType;
import de.coronavirus.imis.domain.Laboratory;
import de.coronavirus.imis.mapper.InstitutionMapper;
import de.coronavirus.imis.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


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
	public InstitutionImpl updateInstitution(InstitutionDTO institutionDTO) {

		final var institution = getInstitution(institutionDTO.getId(), institutionDTO.getInstitutionType());
		institution.setCity(institutionDTO.getCity());
		institution.setComment(institutionDTO.getComment());
		institution.setEmail(institutionDTO.getEmail());
		institution.setHouseNumber(institutionDTO.getHouseNumber());
		institution.setName(institutionDTO.getName());
		institution.setPhoneNumber(institutionDTO.getPhoneNumber());
		institution.setStreet(institutionDTO.getStreet());
		institution.setZip(institutionDTO.getZip());
		return this.institutionRepository.saveAndFlush(institution);
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
	 *
	 * @param id id or a part of an id
	 * @return List of matching institutions
	 */
	@Transactional
	public List<Laboratory> queryLaboratory(String id) {
		return laboratoryRepository.queryLaboratory(id);
	}
}
