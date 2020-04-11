package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.Clinic;
import de.coronavirus.imis.domain.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClinicRepository extends JpaRepository<Clinic, String> {

    List<Laboratory> findByIdContaining(String id);
}
