package de.coronavirus.imis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.coronavirus.imis.domain.LabTest;

public interface LabTestRepository extends JpaRepository<LabTest, String> {
}
