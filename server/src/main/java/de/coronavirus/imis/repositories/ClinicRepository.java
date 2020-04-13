package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.Clinic;
import de.coronavirus.imis.domain.Laboratory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, String> {

	List<Laboratory> findByIdContaining(String id);
}
