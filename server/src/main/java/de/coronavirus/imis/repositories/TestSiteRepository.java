package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.TestSite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestSiteRepository extends JpaRepository<TestSite, String> {
}
