package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.LabTestEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabTestEventRepository extends JpaRepository<LabTestEvent, String> {
}
