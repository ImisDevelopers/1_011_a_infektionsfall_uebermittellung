package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.Laboratory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, String> {

	@Query("select lab from Laboratory lab where " +
			"lower(lab.id) like lower(concat('%', :id, '%')) or " +
			"lower(lab.name) like lower(concat('%', :id, '%')) or " +
			"lower(lab.city) like lower(concat('%', :id, '%'))")
	List<Laboratory> queryLaboratory(String id);

}
