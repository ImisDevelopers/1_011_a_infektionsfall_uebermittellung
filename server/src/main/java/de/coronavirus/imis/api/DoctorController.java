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

@RestController
@RequestMapping("/doctor")
@Slf4j
@RequiredArgsConstructor
public class DoctorController {
    private final PatientEventService eventService;

    @PostMapping("/create_appointment")
    public PatientEvent addScheduledEvent(@RequestBody RequestTestDTO dto) {
        return eventService.createScheduledEvent(dto.getPatientId(), dto.getLaboratoryId(), dto.getDoctorId());
    }
}
