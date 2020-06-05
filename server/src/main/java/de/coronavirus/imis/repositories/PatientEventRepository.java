package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Deprecated
public interface PatientEventRepository extends JpaRepository<PatientEvent, String> {

	List<PatientEvent> findAllByPatient(Patient patient);

	PatientEvent findFirstByPatientOrderByEventTimestampDesc(Patient patient);

}
