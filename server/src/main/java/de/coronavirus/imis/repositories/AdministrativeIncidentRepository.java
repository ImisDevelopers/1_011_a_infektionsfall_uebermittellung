package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.AdministrativeIncident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministrativeIncidentRepository extends JpaRepository<AdministrativeIncident, String> {

	List<AdministrativeIncident> findByPatientId(String patientId);

}
