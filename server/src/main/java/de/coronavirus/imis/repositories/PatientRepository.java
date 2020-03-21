package de.coronavirus.imis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.coronavirus.imis.domain.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {

}
