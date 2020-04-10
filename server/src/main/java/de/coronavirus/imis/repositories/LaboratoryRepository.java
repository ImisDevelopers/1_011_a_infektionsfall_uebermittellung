package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, String> {

    @Query("select lab from Laboratory lab where " +
            "lower(lab.id) like lower(concat('%', :id, '%')) or " +
            "lower(lab.name) like lower(concat('%', :id, '%')) or " +
            "lower(lab.city) like lower(concat('%', :id, '%'))")
    List<Laboratory> queryLaboratory(String id);

}
