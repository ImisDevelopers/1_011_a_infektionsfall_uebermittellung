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

	// TODO: Fix me
//	@Query("select pat from Patient pat where (select info from PatientInformation info where pat = info.patient limit 1)pat.information(0).zip between ?1 and ?2 ")
	List<Patient> findAllByInformation_ZipBetween(String lower, String upperBounds);


	// TODO: Fix me
	@Query("select distinct pat from Patient pat " +
			"left join PatientEvent pe " +
			"on pe.patient = pat.id " +
			"where 1 > (select count(info) from PatientInformation info where lower(info.firstName) like lower(?1) " +
			"AND lower(info.lastName) like lower(?2) " +
			"AND lower(info.id) like lower(?3) " +
			"AND lower(info.gender) like lower(?4) " +
			"AND lower(info.email) like lower(?5) " +
			"AND lower(info.phoneNumber) like lower(?6) " +
			"AND lower(info.street) like lower(?7) " +
			"AND lower(info.houseNumber) like lower(?8) " +
			"AND lower(info.zip) like lower(?9) " +
			"AND lower(info.city) like lower(?10) " +
			"AND lower(info.insuranceCompany) like lower(?11) " +
			"AND lower(info.insuranceMembershipNumber) like lower(?12)) " +
//			"AND lower(coalesce(pe.responsibleDoctor, '0')) like lower(?13) " +
//			"AND lower(coalesce(pe.labTest, '0')) like lower(?14)) > 1 " +
			"AND lower(pat.patientStatus) like lower(?13) ")
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
//											   String doctorId,
//											   String laboratoryId,
											   String patientStatus,
											   Pageable pageable);

	// TODO: Fix me
	@Query("select count(distinct pat) from Patient pat " +
			"left join PatientEvent pe " +
			"on pe.patient = pat.id " +
			"where 1 > (select count(info) from PatientInformation info where lower(info.firstName) like lower(?1) " +
			"AND lower(info.lastName) like lower(?2) " +
			"AND lower(info.id) like lower(?3) " +
			"AND lower(info.gender) like lower(?4) " +
			"AND lower(info.email) like lower(?5) " +
			"AND lower(info.phoneNumber) like lower(?6) " +
			"AND lower(info.street) like lower(?7) " +
			"AND lower(info.houseNumber) like lower(?8) " +
			"AND lower(info.zip) like lower(?9) " +
			"AND lower(info.city) like lower(?10) " +
			"AND lower(info.insuranceCompany) like lower(?11) " +
			"AND lower(info.insuranceMembershipNumber) like lower(?12)) " +
//			"AND lower(coalesce(pe.responsibleDoctor, '0')) like lower(?13) " +
//			"AND lower(coalesce(pe.labTest, '0')) like lower(?14) " +
			"AND lower(pat.patientStatus) like lower(?13) ")
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
//								  String doctorId,
//								  String laboratoryId,
								  String patientStatus);


}
