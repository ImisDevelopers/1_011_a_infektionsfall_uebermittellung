package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.CreatePatientCaseDTO;
import de.coronavirus.imis.domain.PatientCase;
import de.coronavirus.imis.services.PatientCaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/patient-cases")
public class PatientCaseController {

	private final PatientCaseService patientCaseService;

	@GetMapping("/patient/{patientId}")
	public List<PatientCase> getPatientCases(@PathVariable String patientId) {
		return patientCaseService.getCasesForPatient(patientId);
	}

	@PostMapping
	public PatientCase createPatientCase(@RequestBody CreatePatientCaseDTO createPatientCaseDTO) {
		log.info("Creating patient case: " + createPatientCaseDTO);
		return this.patientCaseService.createPatientCase(createPatientCaseDTO);
	}

}
