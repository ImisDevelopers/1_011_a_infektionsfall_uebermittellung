package de.coronavirus.imis.config.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.coronavirus.imis.domain.InstitutionType;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findFirstByRole(InstitutionType institutionType);
}
