package de.coronavirus.imis.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientEvent;

public interface PatientEventRepository extends JpaRepository<PatientEvent, String> {

    List<PatientEvent> findAllByPatient(Patient patient);
}
