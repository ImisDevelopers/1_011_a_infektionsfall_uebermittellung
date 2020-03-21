package de.coronavirus.imis.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import de.coronavirus.imis.api.dto.CreateLabTestDto;
import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.services.LabTestService;

@RestController
@RequestMapping("/labtest")
@AllArgsConstructor
public class LaboratoryEventController {

    private final LabTestService service;

    @PostMapping
    public ResponseEntity<LabTest> createTestForPatient(@RequestBody CreateLabTestDto createLabTestRequest) {
        return null;
    }


}
