package de.coronavirus.imis.services.incidents;

import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.QuarantineIncident;
import de.coronavirus.imis.domain.TestIncident;
import de.coronavirus.imis.repositories.QuarantineIncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuarantineIncidentService {

  private final CommonIncidentService incidentService;
  private final QuarantineIncidentRepository quarantineIncidentRepo;

  public List<QuarantineIncident> getIncidentLogByPatientId(String patientId) {
    return incidentService.getIncidentLogByPatientId(QuarantineIncident.class, patientId);
  }

  public List<QuarantineIncident> getPatientsSelectedForQuarantine() {
    return this.quarantineIncidentRepo.findByEventType(EventType.QUARANTINE_SELECTED);
  }

  public QuarantineIncident save(QuarantineIncident quarantineIncident) {
    return quarantineIncidentRepo.save(quarantineIncident);
  }

}
