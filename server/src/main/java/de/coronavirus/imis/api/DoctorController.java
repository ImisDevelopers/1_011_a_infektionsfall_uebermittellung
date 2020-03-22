package de.coronavirus.imis.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import de.coronavirus.imis.api.dto.RequestTestDTO;
import de.coronavirus.imis.domain.PatientEvent;
import de.coronavirus.imis.services.PatientEventService;
import de.coronavirus.imis.services.PatientService;

@RestController
@RequestMapping("/doctor")
@Slf4j
@RequiredArgsConstructor
public class DoctorController {
    private final PatientEventService eventService;
    private final PatientService patientService;

    @PostMapping("/create_appointment")
    public PatientEvent addScheduledEvent(@RequestBody RequestTestDTO dto) {
        var patient = patientService.findPatientById(dto.getPatientId()).orElseThrow();
        return eventService.createScheduledEvent(patient, dto.getLaboratoryId(), dto.getDoctorId());
    }
}
