package de.coronavirus.imis.api;

import de.coronavirus.imis.services.PatientEventService;
import de.coronavirus.imis.services.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctor")
@Slf4j
@RequiredArgsConstructor
public class DoctorController {
	private final PatientEventService eventService;
	private final PatientService patientService;

	// TODO: This was not used. Do we need this?

//	@PostMapping("/create_appointment")
//	public PatientEvent addScheduledEvent(@RequestBody RequestLabTestDTO dto) {
//		var patient = patientService.findPatientById(dto.getPatientId()).orElseThrow();
//		return eventService.createScheduledEvent(patient, dto.getLaboratoryId(), dto.getDoctorId());
//	}
}
