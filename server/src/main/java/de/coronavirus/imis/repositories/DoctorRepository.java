package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.Doctor;
import de.coronavirus.imis.domain.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, String> {

	List<Laboratory> findByIdContaining(String id);

}
