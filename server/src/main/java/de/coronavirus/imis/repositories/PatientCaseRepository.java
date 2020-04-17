package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.PatientCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientCaseRepository extends JpaRepository<PatientCase, String> {
}
