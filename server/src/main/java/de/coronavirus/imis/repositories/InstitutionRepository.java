package de.coronavirus.imis.repositories;

import java.util.Optional;

import de.coronavirus.imis.domain.InstitutionImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import de.coronavirus.imis.domain.InstitutionImpl;

public interface InstitutionRepository extends JpaRepository<InstitutionImpl, String> {}
