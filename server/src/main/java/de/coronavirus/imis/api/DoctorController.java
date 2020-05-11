package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.RequestLabTestDTO;
import de.coronavirus.imis.domain.PatientEvent;
import de.coronavirus.imis.services.IncidentService;
import de.coronavirus.imis.services.PatientEventService;
import de.coronavirus.imis.services.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctor")
@Slf4j
@RequiredArgsConstructor
public class DoctorController {
	private final PatientEventService eventService;
	private final PatientService patientService;
	private final IncidentService incidentService;

	@PostMapping("/create_appointment")
	public PatientEvent addScheduledEvent(@RequestBody RequestLabTestDTO dto) {
		var patient = patientService.findPatientById(dto.getPatientId()).orElseThrow();
		incidentService.addIncident(patient, dto.getLaboratoryId(), dto.getDoctorId());
		return eventService.createScheduledEvent(patient, dto.getLaboratoryId(), dto.getDoctorId());
	}
}
