package de.coronavirus.imis.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.coronavirus.imis.domain.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    @Query("select pat from Patient pat where pat.zip >= ?1 and pat.zip <= ?2 ")
    List<Patient> findAllByZipBetween(Integer lower, Integer upperBounds);
}
