package de.coronavirus.imis.api.incidents;

import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.QuarantineIncident;
import de.coronavirus.imis.services.incidents.QuarantineIncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/incidents")
public class QuarantineIncidentController {

  private final QuarantineIncidentService quarantineIncidentService;

  @GetMapping("/selected-for-quarantine")
  public List<QuarantineIncident> getSelectedForQuarantine() {
    return quarantineIncidentService.getPatientsSelectedForQuarantine();
  }

  @PostMapping("/quarantine")
  public QuarantineIncident addOrUpdateQuarantineIncident(@RequestBody QuarantineIncident quarantineIncident) {

    // TODO This is only necessary because we do not have the current QuarantineIncident in the frontend
    // Therefore, requesting a quarantine multiple times will lead to parallel QuarantineIncidents with type QUARANTINE_SELECTED.
    // To prevent this, fetch the latest QuarantineIncident and check if it was SELECTED (as opposed to MANDATED)
    // The frontend, however, should have knowledge about existing QuarantineIncidents and send them to update directly
    if (quarantineIncidentService.getIncidentById(quarantineIncident.getId()).isEmpty()) {
      Optional<QuarantineIncident> latest = quarantineIncidentService.getLatestForPatient(quarantineIncident.getPatient());
      if (latest.isPresent() && latest.get().getEventType().equals(EventType.QUARANTINE_SELECTED)) {
        quarantineIncident.setId(latest.get().getId());
      }
    }
    // end TODO

    return quarantineIncidentService.save(quarantineIncident);
  }

  @PostMapping("/quarantine/bulk")
  public List<QuarantineIncident> addOrUpdateMultipleQuarantineIncidents(@RequestBody List<QuarantineIncident> quarantineIncidents) {
    return quarantineIncidents.stream().map(quarantineIncidentService::save).collect(Collectors.toList());
  }

}
