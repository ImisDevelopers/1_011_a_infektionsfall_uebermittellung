package de.coronavirus.imis.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.coronavirus.imis.domain.LabTest;

@Repository
public interface LabTestRepository extends JpaRepository<LabTest, String> {

    Optional<LabTest> findFirstByTestIdAndLaboratoryId(String testId, String laboratoryId);
}
