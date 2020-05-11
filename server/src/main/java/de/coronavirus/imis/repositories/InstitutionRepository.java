package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.InstitutionImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<InstitutionImpl, String> {
}
