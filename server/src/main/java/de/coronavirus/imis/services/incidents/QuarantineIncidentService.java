package de.coronavirus.imis.services.incidents;

import de.coronavirus.imis.domain.*;
import de.coronavirus.imis.repositories.QuarantineIncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuarantineIncidentService {

  private final CommonIncidentService incidentService;
  private final QuarantineIncidentRepository quarantineIncidentRepo;

  public List<QuarantineIncident> getIncidentLogByPatientId(String patientId) {
    return incidentService.getIncidentLogByPatientId(QuarantineIncident.class, patientId);
  }

  public List<QuarantineIncident> getPatientsSelectedForQuarantine() {
    return quarantineIncidentRepo.findByEventType(EventType.QUARANTINE_SELECTED);
  }

  public Optional<QuarantineIncident> getLatestForPatient(Patient patient) {
    return quarantineIncidentRepo.findByPatientId(patient.getId())
      .stream()
      .max(Comparator.comparing(QuarantineIncident::getVersionTimestamp));
  }

  public boolean hasActiveQuarantine(Patient patient) {
    var latest = getLatestForPatient(patient);
    if (!latest.isPresent()) {
      return false;
    }

    return latest.get().getEventType().equals(EventType.QUARANTINE_MANDATED)
      && (latest.get().getUntil().isEqual(LocalDate.now())
      || latest.get().getUntil().isAfter(LocalDate.now()));
  }

  public QuarantineIncident save(QuarantineIncident quarantineIncident) {
    return quarantineIncidentRepo.saveAndFlush(quarantineIncident);
  }

  public Optional<QuarantineIncident> getIncidentById(String id) {
    return quarantineIncidentRepo.findById(id);
  }
}
