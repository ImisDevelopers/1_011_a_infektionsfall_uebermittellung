package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.Patient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String>, JpaSpecificationExecutor<Patient> {
	@Query("select pat from Patient pat where pat.zip between ?1 and ?2 ")
	List<Patient> findAllByZipBetween(String lower, String upperBounds);

	@Query("select distinct pat from Patient pat " +
			"left join PatientEvent pe " +
			"on pe.patient = pat.id " +
			"where lower(pat.firstName) like lower(?1) " +
			"AND lower(pat.lastName) like lower(?2) " +
			"AND lower(pat.id) like lower(?3) " +
			"AND lower(pat.gender) like lower(?4) " +
			"AND lower(pat.email) like lower(?5) " +
			"AND lower(pat.phoneNumber) like lower(?6) " +
			"AND lower(pat.street) like lower(?7) " +
			"AND lower(pat.houseNumber) like lower(?8) " +
			"AND lower(pat.zip) like lower(?9) " +
			"AND lower(pat.city) like lower(?10) " +
			"AND lower(pat.insuranceCompany) like lower(?11) " +
			"AND lower(pat.insuranceMembershipNumber) like lower(?12) " +
			"AND lower(coalesce(pe.responsibleDoctor, '0')) like lower(?13) " +
			"AND lower(coalesce(pe.labTest, '0')) like lower(?14) " +
			"AND lower(pat.patientStatus) like lower(?15) ")
	List<Patient> findAllByPatientSearchParams(String firstName,
											   String lastName,
											   String id,
											   String gender,
											   String email,
											   String phoneNumber,
											   String street,
											   String houseNumber,
											   String zip,
											   String city,
											   String insuranceCompany,
											   String insuranceMembershipNumber,
											   String doctorId,
											   String laboratoryId,
											   String patientStatus,
											   Pageable pageable);

	@Query("select count(distinct pat) from Patient pat " +
			"left join PatientEvent pe " +
			"on pe.patient = pat.id " +
			"where lower(pat.firstName) like lower(?1) " +
			"AND lower(pat.lastName) like lower(?2) " +
			"AND lower(pat.id) like lower(?3) " +
			"AND lower(pat.gender) like lower(?4) " +
			"AND lower(pat.email) like lower(?5) " +
			"AND lower(pat.phoneNumber) like lower(?6) " +
			"AND lower(pat.street) like lower(?7) " +
			"AND lower(pat.houseNumber) like lower(?8) " +
			"AND lower(pat.zip) like lower(?9) " +
			"AND lower(pat.city) like lower(?10) " +
			"AND lower(pat.insuranceCompany) like lower(?11) " +
			"AND lower(pat.insuranceMembershipNumber) like lower(?12) " +
			"AND lower(coalesce(pe.responsibleDoctor, '0')) like lower(?13) " +
			"AND lower(coalesce(pe.labTest, '0')) like lower(?14) " +
			"AND lower(pat.patientStatus) like lower(?15) ")
	Long countPatientSearchParams(String firstName,
								  String lastName,
								  String id,
								  String gender,
								  String email,
								  String phoneNumber,
								  String street,
								  String houseNumber,
								  String zip,
								  String city,
								  String insuranceCompany,
								  String insuranceMembershipNumber,
								  String doctorId,
								  String laboratoryId,
								  String patientStatus);


}
