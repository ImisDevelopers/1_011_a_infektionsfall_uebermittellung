package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.CreateLabTestDTO;
import de.coronavirus.imis.api.dto.UpdateTestStatusDTO;
import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.services.IncidentServiceWithCompatibility;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Deprecated
@RestController
@RequestMapping("/api/labtests")
@AllArgsConstructor
public class LabTestController {

	//private final LabTestService service;
	private final IncidentServiceWithCompatibility incidentService;

	@PostMapping
	public ResponseEntity<LabTest> createTestForPatient(@RequestBody CreateLabTestDTO createLabTestRequest) {
		return ResponseEntity.ok(incidentService.addIncident(createLabTestRequest));
	}

	@GetMapping("/query")
	public List<LabTest> queryLabTestsById(@RequestParam String labTestIdQuery) {
		return incidentService.queryLabTestsById(labTestIdQuery);
	}

	@GetMapping("/patient/{id}")
	public List<LabTest> getLabTestForPatient(@PathVariable("id") String patientId) {
		return incidentService.getAllTestIncidentForPatient(patientId);
	}

	@PutMapping("/{laboratoryId}")
	public ResponseEntity<LabTest> updateTestStatus(@PathVariable("laboratoryId") String laboratoryId, @RequestBody UpdateTestStatusDTO statusDTO) {
		return ResponseEntity.ok(incidentService.updateIncident(laboratoryId, statusDTO));
	}
}
