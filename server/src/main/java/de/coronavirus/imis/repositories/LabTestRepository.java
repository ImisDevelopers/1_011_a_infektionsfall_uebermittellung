package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.LabTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Deprecated
public interface LabTestRepository extends JpaRepository<LabTest, String> {

	Optional<LabTest> findFirstByTestIdAndLaboratoryId(String testId, String laboratoryId);

	List<LabTest> findByTestIdContaining(String labTestId);
}
