package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, String> {
}
