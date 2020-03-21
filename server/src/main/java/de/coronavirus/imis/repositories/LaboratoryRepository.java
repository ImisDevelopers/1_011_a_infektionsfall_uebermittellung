package de.coronavirus.imis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.coronavirus.imis.domain.Laboratory;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory,Long> {
}
