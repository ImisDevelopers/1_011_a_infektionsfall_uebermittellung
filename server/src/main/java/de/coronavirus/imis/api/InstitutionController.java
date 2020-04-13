package de.coronavirus.imis.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.api.dto.CreateInstitutionDTO;
import de.coronavirus.imis.api.dto.InstitutionDTO;
import de.coronavirus.imis.domain.Doctor;
import de.coronavirus.imis.domain.Laboratory;
import de.coronavirus.imis.mapper.InstitutionMapper;
import de.coronavirus.imis.services.InstitutionService;

@RestController
@RequestMapping("/api/institutions")
@RequiredArgsConstructor
public class InstitutionController {

    final InstitutionService institutionService;
    final InstitutionMapper institutionMapper;

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
