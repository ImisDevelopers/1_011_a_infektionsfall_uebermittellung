package de.coronavirus.imis.services.incidents;

import de.coronavirus.imis.domain.AdministrativeIncident;
import de.coronavirus.imis.domain.HospitalizationIncident;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdministrativeIncidentService {

  private final CommonIncidentService incidentService;

  public List<AdministrativeIncident> getIncidentLogByPatientId(String patientId) {
    return incidentService.getIncidentLogByPatientId(AdministrativeIncident.class, patientId);
  }

}
