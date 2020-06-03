package de.coronavirus.imis.api.incidents;

import de.coronavirus.imis.domain.QuarantineIncident;
import de.coronavirus.imis.services.incidents.QuarantineIncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/incidents")
public class QuarantineIncidentController {

  private final QuarantineIncidentService quarantineIncidentService;

  @GetMapping("/selected-for-quarantine")
  public List<QuarantineIncident> getSelectedForQuarantine() {
    return quarantineIncidentService.getPatientsSelectedForQuarantine();
  }

}
