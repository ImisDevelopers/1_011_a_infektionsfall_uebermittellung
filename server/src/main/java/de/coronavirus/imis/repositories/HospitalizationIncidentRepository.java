package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.AdministrativeIncident;
import de.coronavirus.imis.domain.HospitalizationIncident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalizationIncidentRepository extends JpaRepository<HospitalizationIncident, String> {

	List<AdministrativeIncident> findByPatientId(String patientId);

}

