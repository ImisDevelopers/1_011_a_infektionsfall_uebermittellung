package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {

    @Override
    Optional<Patient> findById(Long id);

    @Override
    Iterable<Patient> findAll();
}
