package de.coronavirus.imis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import de.coronavirus.imis.domain.ExposureContact;

public interface ExposureContactRepository extends JpaRepository<ExposureContact, Long> {

	List<ExposureContact> findBySourceId(String patientId);
	List<ExposureContact> findByContactId(String patientId);

	Optional<ExposureContact> findBySourceIdAndContactId(String sourceId, String contactId);
}
