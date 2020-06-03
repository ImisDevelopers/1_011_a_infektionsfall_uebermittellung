package de.coronavirus.imis.services.incidents;

import de.coronavirus.imis.domain.HospitalizationIncident;
import de.coronavirus.imis.domain.TestIncident;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HospitalizationIncidentService {

  private final CommonIncidentService incidentService;

  public List<HospitalizationIncident> getIncidentLogByPatientId(String patientId) {
    return incidentService.getIncidentLogByPatientId(HospitalizationIncident.class, patientId);
  }

}
