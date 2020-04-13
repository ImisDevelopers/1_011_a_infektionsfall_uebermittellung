package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.CreateInstitutionDTO;
import de.coronavirus.imis.api.dto.InstitutionDTO;
import de.coronavirus.imis.domain.Doctor;
import de.coronavirus.imis.domain.Laboratory;
import de.coronavirus.imis.services.InstitutionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/institutions")
@RequiredArgsConstructor
public class InstitutionController {

	final InstitutionService institutionService;

	@PostMapping
	public ResponseEntity<InstitutionDTO> createInstitution(@RequestBody CreateInstitutionDTO createInstitutionDTO) {

		var dto = new InstitutionDTO();

		var type = createInstitutionDTO.getInstitutionType();

		switch (type) {
			case DEPARTMENT_OF_HEALTH:
				var departmentOfHealth = this.institutionService.createDepartmentOfHealthInstitution(createInstitutionDTO);
				dto = InstitutionDTO.builder()
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
				break;
			case LABORATORY:
				var laboratory = this.institutionService.createLaboratoryInstitution(createInstitutionDTO);
				dto = InstitutionDTO.builder()
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
				break;
			case TEST_SITE:
				var testSite = this.institutionService.createTestSiteInstitution(createInstitutionDTO);
				dto = InstitutionDTO.builder()
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
				break;
			case CLINIC:
				var clinic = this.institutionService.createClinicInstitution(createInstitutionDTO);
				dto = InstitutionDTO.builder()
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
				break;
			case DOCTORS_OFFICE:
				var doctor = this.institutionService.createDoctorInstitution(createInstitutionDTO);
				dto = InstitutionDTO.builder()
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
				break;
		}

		return ResponseEntity.ok(dto);
	}

	@GetMapping("/laboratories")
	public List<Laboratory> getAllLaboratories() {
		return institutionService.getAllLaboratories();
	}

	@GetMapping("/laboratories/query")
	public List<Laboratory> queryAllLaboratories(@RequestParam String id) {
		return institutionService.queryLaboratory(id);
	}

	@GetMapping("/doctors")
	public List<Doctor> getAllDoctors() {
		return institutionService.getAllDoctors();
	}
}
