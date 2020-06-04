package de.coronavirus.imis.api.incidents;

import de.coronavirus.imis.domain.QuarantineIncident;
import de.coronavirus.imis.services.incidents.QuarantineIncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    return quarantineIncidentService.save(quarantineIncident);
  }

  @PostMapping("/quarantine/bulk")
  public List<QuarantineIncident> addOrUpdateMultipleQuarantineIncidents(@RequestBody List<QuarantineIncident> quarantineIncidents) {
    return quarantineIncidents.stream().map(quarantineIncidentService::save).collect(Collectors.toList());
  }

}
