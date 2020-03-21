package de.coronavirus.imis.api;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import de.coronavirus.imis.api.dto.CreateLabTestDTO;
import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.services.LabTestService;

@RestController
@RequestMapping("/labtest")
@AllArgsConstructor
public class LabTestController {

    private final LabTestService service;

    @PostMapping
    public ResponseEntity<LabTest> createTestForPatient(@RequestBody CreateLabTestDTO createLabTestRequest) {
        return ResponseEntity.ok(service.createLabTest(createLabTestRequest.getPatientId(),
                Long.valueOf(createLabTestRequest.getLaboratoryId()),
                createLabTestRequest.getLabInternalId()));
    }

    @GetMapping("/patient/{id}")
    public Set<LabTest> getLabTestForPatient(@PathVariable("id") String patientId) {
        return service.getAllLabTestForPatient(patientId);
    }

    @PutMapping("/{id}")
    public void updateTestStatus(@PathVariable("id") String id){

    }


}
