package de.coronavirus.imis.api;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import de.coronavirus.imis.api.dto.CreateLabTestDTO;
import de.coronavirus.imis.api.dto.UpdateTestStatusDTO;
import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.services.LabTestService;

@RestController
@RequestMapping("/api/labtests")
@AllArgsConstructor
public class LabTestController {

    private final LabTestService service;

    @PostMapping
    public ResponseEntity<LabTest> createTestForPatient(@RequestBody CreateLabTestDTO createLabTestRequest) {
        return ResponseEntity.ok(service.createLabTest(
                createLabTestRequest.getPatientId(),
                createLabTestRequest.getLaboratoryId(),
                createLabTestRequest.getTestId(),
                createLabTestRequest.getComment(),
                createLabTestRequest.getTestType())
        );
    }

    @GetMapping("/query")
    public List<LabTest> queryLabTestsById(@RequestParam String labTestIdQuery) {
        return service.queryLabTestsById(labTestIdQuery);
    }

    @GetMapping("/patient/{id}")
    public Set<LabTest> getLabTestForPatient(@PathVariable("id") String patientId) {
        return service.getAllLabsTestForPatient(patientId);
    }

    @PutMapping("/{laboratoryId}")
    public ResponseEntity<LabTest> updateTestStatus(@PathVariable("laboratoryId") String laboratoryId, @RequestBody UpdateTestStatusDTO statusDTO) {
        return ResponseEntity.ok(service.updateTestStatus(
                statusDTO.getTestId(),
                laboratoryId,
                statusDTO.getStatus(),
                statusDTO.getComment(),
                statusDTO.getFile())
        );
    }
}
