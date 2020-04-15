package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.CreateInstitutionDTO;
import de.coronavirus.imis.api.dto.InstitutionDTO;
import de.coronavirus.imis.domain.Doctor;
import de.coronavirus.imis.domain.Laboratory;
import de.coronavirus.imis.mapper.InstitutionMapper;
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
    final InstitutionMapper institutionMapper;

    @PutMapping
    public InstitutionDTO updateInstitution(@RequestBody InstitutionDTO institutionDTO) {
        return institutionMapper.toInstitutionDTO(institutionService.updateInstitution(institutionDTO));
    }

    @PostMapping
    public ResponseEntity<InstitutionDTO> createInstitution(@RequestBody CreateInstitutionDTO createInstitutionDTO) {

        var institution = institutionService.addInstitution(
            institutionMapper.toInstitution(createInstitutionDTO));
        var responseDto = institutionMapper.toInstitutionDTO(institution);

        return ResponseEntity.ok(responseDto);
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
