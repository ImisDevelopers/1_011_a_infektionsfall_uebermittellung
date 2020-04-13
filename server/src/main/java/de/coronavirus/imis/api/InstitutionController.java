package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.CreateInstitutionDTO;
import de.coronavirus.imis.api.dto.InstitutionDTO;
import de.coronavirus.imis.domain.*;
import de.coronavirus.imis.services.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/institutions")
@RequiredArgsConstructor
public class InstitutionController {

    final InstitutionService institutionService;

    @PutMapping
    public InstitutionDTO updateInstitution(@RequestBody InstitutionDTO institutionDTO) {
        return institutionService.mapInstitution(institutionService.updateInstitution(institutionDTO));
    }

    @PostMapping
    public ResponseEntity<InstitutionDTO> createInstitution(@RequestBody CreateInstitutionDTO createInstitutionDTO) {

        Institution institution;

        var type = createInstitutionDTO.getInstitutionType();

        switch (type) {
            case DEPARTMENT_OF_HEALTH:
                institution = this.institutionService.createDepartmentOfHealthInstitution(createInstitutionDTO);
                break;
            case LABORATORY:
                institution = this.institutionService.createLaboratoryInstitution(createInstitutionDTO);
                break;
            case TEST_SITE:
                institution = this.institutionService.createTestSiteInstitution(createInstitutionDTO);
                break;
            case CLINIC:
                institution = this.institutionService.createClinicInstitution(createInstitutionDTO);
                break;
            case DOCTORS_OFFICE:
                institution = this.institutionService.createDoctorInstitution(createInstitutionDTO);
                break;
            default:
            case GOVERNMENT_AGENCY:
                // TODO: Government agency
                throw new RuntimeException(type + " not implemented yet");
        }

        return ResponseEntity.ok(institutionService.mapInstitution(institution));
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
