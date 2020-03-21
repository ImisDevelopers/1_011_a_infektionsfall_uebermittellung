package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, String> {
    @Query("SELECT zip, COUNT(*) FROM Patient GROUP BY zip")
    List<Object[]> getCasesGroupedZip();
}
