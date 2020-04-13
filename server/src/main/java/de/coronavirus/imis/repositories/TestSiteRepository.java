package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.Laboratory;
import de.coronavirus.imis.domain.TestSite;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestSiteRepository extends JpaRepository<TestSite, String> {

	List<Laboratory> findByIdContaining(String id);

}
