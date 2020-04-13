package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.api.dto.PatientSearchParamsDTO;
import de.coronavirus.imis.api.dto.PatientSimpleSearchParamsDTO;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.services.PatientService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

	private final PatientService patientService;

	@PostMapping
	public ResponseEntity<Patient> addPatient(@RequestBody CreatePatientDTO dto) {
		var patient = patientService.addPatient(dto);
		if (patient == null) {
			return ResponseEntity.status(500).build();
		}
		return ResponseEntity.ok(patient);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Patient> getPatientForId(@PathVariable("id") String id) {
		return patientService.findPatientById(id).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<Patient>> getAllPatients() {
		return ResponseEntity.ok(patientService.getAllPatients());
	}


	@PostMapping("/query-simple")
	@PreAuthorize("hasAnyRole('CLINIC', 'DOCTORS_OFFICE', 'TEST_SITE')")
	public List<Patient> queryPatientsSimple(@RequestBody PatientSimpleSearchParamsDTO query) {
		return patientService.queryPatientsSimple(query);
	}

	@GetMapping("/query-simple/count")
	@PreAuthorize("hasAnyRole('CLINIC', 'DOCTORS_OFFICE', 'TEST_SITE')")
	public Long countQueryPatientsSimple(@RequestParam String query) {
		return patientService.queryPatientsSimpleCount(query);
	}

	@PostMapping("/query")
	@PreAuthorize("hasAnyRole('CLINIC', 'DOCTORS_OFFICE', 'TEST_SITE')")
	public List<Patient> queryPatients(@RequestBody final PatientSearchParamsDTO patientSearchParamsDTO) {
		return patientService.queryPatients(patientSearchParamsDTO);
	}

	@PostMapping("/query/count")
	@PreAuthorize("hasAnyRole('CLINIC', 'DOCTORS_OFFICE', 'TEST_SITE')")
	public Long countQueryPatients(@RequestBody final PatientSearchParamsDTO patientSearchParamsDTO) {
		return patientService.countQueryPatients(patientSearchParamsDTO);
	}
}
