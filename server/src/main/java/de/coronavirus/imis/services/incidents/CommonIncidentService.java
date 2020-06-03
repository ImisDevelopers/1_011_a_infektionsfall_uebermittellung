package de.coronavirus.imis.services.incidents;

import de.coronavirus.imis.domain.Incident;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommonIncidentService {

  private final AuditReader auditReader;

  @SuppressWarnings("unchecked")
  public <T extends Incident> List<T> getIncidentLogByPatientId(Class<T> T, String patientId) {
    var query = auditReader.createQuery().forRevisionsOfEntity(T, true, false);
    query.add(AuditEntity.relatedId("patient").eq(patientId));
    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  public <T extends Incident> List<T> getIncidentLogByIncidentId(Class<T> T, String incidentId) {
    var query = auditReader.createQuery().forRevisionsOfEntity(T, true, false);
    query.add(AuditEntity.id().eq(incidentId));
    return query.getResultList();
  }

}
