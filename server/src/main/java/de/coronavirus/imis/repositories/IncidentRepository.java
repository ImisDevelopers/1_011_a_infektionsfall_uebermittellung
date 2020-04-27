package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, String> {

	

}
