package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.QuarantineIncident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuarantineIncidentRepository extends JpaRepository<QuarantineIncident, String> {

	List<QuarantineIncident> findByPatientId(String patientId);

	List<QuarantineIncident> findByEventType(EventType eventType);

}
