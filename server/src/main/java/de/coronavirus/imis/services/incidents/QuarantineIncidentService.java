package de.coronavirus.imis.services.incidents;

import de.coronavirus.imis.domain.*;
import de.coronavirus.imis.repositories.QuarantineIncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
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
      && latest.get().getUntil().isEqual(LocalDate.now())
      || latest.get().getUntil().isAfter(LocalDate.now());
  }

  public QuarantineIncident save(QuarantineIncident quarantineIncident) {
    return quarantineIncidentRepo.save(quarantineIncident);
  }

  @Transactional
  public void updateQuarantineIncident(String patientId, EventType status, LocalDate date) {

		/*
			There's only one QuarantineIncident per Person which is why we can find it without Incident Id here.

			Note:
			Having only one QuarantineIncident per Person is technically incorrect. If a patient is quarantined,
			released and quarantined again, that should be a new incident (a new incident ID).
			This improvement can be applied once there's a agreed strategy on how the frontend handles incidents.
		 */

    var quarantineIncidents = quarantineIncidentRepo.findByPatientId(patientId);
    if (quarantineIncidents.isEmpty()) {
      throw new QuarantineNotFoundException("No existing quarantine incident found for " + patientId);
    }

    var incident = quarantineIncidents.get(0);

    incident
      .setEventDate(date == null ? LocalDate.now() : date)
      .setEventType(status);
    quarantineIncidentRepo.saveAndFlush(incident);
  }

}
