package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientEvent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientEventRepository extends JpaRepository<PatientEvent, String> {

	List<PatientEvent> findAllByPatient(Patient patient);

	List<PatientEvent> findPatientEventByLabTest(LabTest labTest);

	PatientEvent findFirstByPatientOrderByEventTimestampDesc(Patient patient);

}
