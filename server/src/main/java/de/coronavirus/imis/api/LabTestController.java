package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.CreateLabTestDTO;
import de.coronavirus.imis.api.dto.UpdateTestStatusDTO;
import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.services.incidents.WriteIncidentService;
import de.coronavirus.imis.services.LabTestService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/labtests")
@AllArgsConstructor
public class LabTestController {

	private final LabTestService service;
	private final WriteIncidentService incidentService;

	@PostMapping
	@ApiResponses({
		@ApiResponse(code = 400, message = "Error Condition", response = LabTest.ConstraintViolationException.Response.class)
	})
	public LabTest createTestForPatient(@RequestBody CreateLabTestDTO createLabTestRequest)
	 	throws LabTest.ConstraintViolationException {
		incidentService.addTestIncident(createLabTestRequest);
		return service.createLabTest(createLabTestRequest);
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
		//incidentService.updateTestIncident(laboratoryId, statusDTO);
		return ResponseEntity.ok(service.updateTestStatus(
				laboratoryId, statusDTO
		));
	}
}
