package de.coronavirus.imis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.coronavirus.imis.domain.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, String> {
}
