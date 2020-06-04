package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.Patient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.SortedSet;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String>, JpaSpecificationExecutor<Patient> {
	@Query("select pat from Patient pat where pat.zip between ?1 and ?2 ")
	List<Patient> findAllByZipBetween(String lower, String upperBounds);

	@Query("SELECT DISTINCT p.insuranceCompany FROM Patient p")
	SortedSet<String> findDistinctInsuranceCompanies();
}
