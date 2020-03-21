package de.coronavirus.imis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.coronavirus.imis.domain.LabTest;
import org.springframework.stereotype.Repository;

@Repository
public interface LabTestRepository extends JpaRepository<LabTest, String> {
}
