package de.coronavirus.imis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.coronavirus.imis.domain.PatientEvent;

public interface PatientEventRepository extends JpaRepository<PatientEvent, String> {
}
