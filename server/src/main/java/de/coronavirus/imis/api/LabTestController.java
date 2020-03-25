package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.CreateLabTestDTO;
import de.coronavirus.imis.api.dto.UpdateTestStatusDTO;
import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.domain.PatientEvent;
import de.coronavirus.imis.services.LabTestService;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/labtests")
@AllArgsConstructor
public class LabTestController {

    private final LabTestService service;

    @PostMapping
    public ResponseEntity<LabTest> createTestForPatient(@RequestBody CreateLabTestDTO createLabTestRequest) {
        return ResponseEntity.ok(service.createLabTest(
                createLabTestRequest.getPatientId(),
                createLabTestRequest.getLaboratoryId(),
                createLabTestRequest.getTestId(),
                createLabTestRequest.getComment())
        );
    }

    @GetMapping("/patient/{id}")
    public Set<LabTest> getLabTestForPatient(@PathVariable("id") String patientId) {
        return service.getAllLabsTestForPatient(patientId);
    }

    @PutMapping("/{id}")
    public PatientEvent updateTestStatus(@PathVariable("id") String id, @RequestBody UpdateTestStatusDTO statusDTO) {
        return service.updateTestStatus(id, statusDTO.getStatus());
    }


}
