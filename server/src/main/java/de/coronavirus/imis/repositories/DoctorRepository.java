package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.Doctor;
import de.coronavirus.imis.domain.Laboratory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, String> {

	List<Laboratory> findByIdContaining(String id);

}
