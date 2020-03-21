package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.CreateLabTestDTO;
import de.coronavirus.imis.api.dto.UpdateStatusDTO;
import de.coronavirus.imis.api.dto.UpdateTestStatusDTO;
import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.domain.PatientEvent;
import de.coronavirus.imis.services.LabTestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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


    @PatchMapping("/{id}")
    public PatientEvent updateTestStatus(@PathVariable("id") String id, @RequestBody UpdateTestStatusDTO statusDTO) {
        return service.updateTestStatus(id, statusDTO.getUpdatedTestStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabTest> getLabTestForId(@PathVariable("id") String id) {
        return service.findLabTestById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
