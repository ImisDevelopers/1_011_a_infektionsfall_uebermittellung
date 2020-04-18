package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.CreateLabTestDTO;
import de.coronavirus.imis.api.dto.UpdateTestStatusDTO;
import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.services.LabTestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/labtests")
@AllArgsConstructor
public class LabTestController {

	private final LabTestService service;

	@PostMapping
	public ResponseEntity<LabTest> createTestForPatient(@RequestBody CreateLabTestDTO createLabTestRequest) {
		log.info("Creating lab test " + createLabTestRequest);
		return ResponseEntity.ok(
				service.createLabTest(createLabTestRequest)
		);
	}

	@GetMapping("/query")
	public List<LabTest> queryLabTestsById(@RequestParam String labTestIdQuery) {
		return service.queryLabTestsById(labTestIdQuery);
	}

	@GetMapping("/patient/{id}")
	public List<LabTest> getLabTestForPatient(@PathVariable("id") String patientId) {
		return service.getAllLabsTestForPatient(patientId);
	}

	@PutMapping("/{laboratoryId}")
	public ResponseEntity<LabTest> updateTestStatus(@PathVariable("laboratoryId") String laboratoryId, @RequestBody UpdateTestStatusDTO statusDTO) {
		return ResponseEntity.ok(service.updateTestStatus(
				laboratoryId, statusDTO
		));
	}
}
