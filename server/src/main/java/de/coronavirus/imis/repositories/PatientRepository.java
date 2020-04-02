package de.coronavirus.imis.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.coronavirus.imis.domain.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    @Query("select pat from Patient pat where pat.zip >= ?1 and pat.zip <= ?2 ")
    List<Patient> findAllByZipBetween(Integer lower, Integer upperBounds);

    @Query("select distinct pat from Patient pat " +
            "left join PatientEvent pe " +
            "on pe.patient = pat.id " +
            "where lower(pat.firstName) like ?1 " +
            "AND lower(pat.lastName) like lower(?2) " +
            "AND lower(pat.id) like lower(?3) " +
            "AND lower(pat.gender) like lower(?4) " +
            "AND lower(pat.email) like lower(?5) " +
            "AND lower(pat.phoneNumber) like lower(?6) " +
            "AND lower(pat.street) like lower(?7) " +
            "AND lower(pat.houseNumber) like lower(?8) " +
            "AND lower(pat.zip) LIKE lower(?9)" +
            "AND lower(pat.city) like lower(?10) " +
            "AND lower(pat.insuranceCompany) like lower(?11) " +
            "AND lower(pat.insuranceMembershipNumber) like lower(?12) " +
            "AND pat.confirmed = ?13 " +
            "AND lower(pe.responsibleDoctor) = lower(?14) " +
            "AND lower(pe.labTest) = lower(?15) ")
    List<Patient> findAllByPatientSearchParams(String firstName,
                                               String lastName,
                                               String id,
                                               String gender,
                                               String email,
                                               String phoneNumber,
                                               String street,
                                               String houseNumber,
                                               Integer zip,
                                               String city,
                                               String insuranceCompany,
                                               String insuranceMembershipNumber,
                                               Boolean confirmed,
                                               String doctorId,
                                               String laboratoryId,
                                               Pageable pageable);

    @Query("select distinct count(pat) from Patient pat " +
            "left join PatientEvent pe " +
            "on pe.patient = pat.id " +
            "where lower(pat.firstName) like ?1 " +
            "AND lower(pat.lastName) like lower(?2) " +
            "AND lower(pat.id) like lower(?3) " +
            "AND lower(pat.gender) like lower(?4) " +
            "AND lower(pat.email) like lower(?5) " +
            "AND lower(pat.phoneNumber) like lower(?6) " +
            "AND lower(pat.street) like lower(?7) " +
            "AND lower(pat.houseNumber) like lower(?8) " +
            "AND lower(pat.zip) LIKE lower(?9)" +
            "AND lower(pat.city) like lower(?10) " +
            "AND lower(pat.insuranceCompany) like lower(?11) " +
            "AND lower(pat.insuranceMembershipNumber) like lower(?12) " +
            "AND pat.confirmed = ?13 " +
            "AND lower(pe.responsibleDoctor) = lower(?14) " +
            "AND lower(pe.labTest) = lower(?15) ")
    Long countPatientSearchParams(String firstName,
                                  String lastName,
                                  String id,
                                  String gender,
                                  String email,
                                  String phoneNumber,
                                  String street,
                                  String houseNumber,
                                  Integer zip,
                                  String city,
                                  String insuranceCompany,
                                  String insuranceMembershipNumber,
                                  Boolean confirmed,
                                  String doctorId,
                                  String laboratoryId);


}
