package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.CreateInstitutionDTO;
import de.coronavirus.imis.domain.*;
import de.coronavirus.imis.repositories.ClinicRepository;
import de.coronavirus.imis.repositories.DoctorRepository;
import de.coronavirus.imis.repositories.LaboratoryRepository;
import de.coronavirus.imis.repositories.TestSiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;



@Service
@RequiredArgsConstructor
public class InstitutionService {
    private final LaboratoryRepository laboratoryRepository;
    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;
    private final TestSiteRepository testSiteRepository;


    @Transactional
    public Doctor createDoctorInstitution(CreateInstitutionDTO institutionDTO) {
       var doctor = new Doctor();
       doctor.setName(institutionDTO.getName());
       doctor.setStreet(institutionDTO.getStreet());
       doctor.setHouseNumber(institutionDTO.getHouseNumber());
       doctor.setZip(institutionDTO.getZip());
       doctor.setCity(institutionDTO.getCity());
       doctor.setEmail(institutionDTO.getEmail());
       doctor.setPhoneNumber(institutionDTO.getPhoneNumber());
       doctor.setComment(institutionDTO.getComment());

       return this.doctorRepository.saveAndFlush(doctor);
    }

    @Transactional
    public Laboratory createLaboratoryInstitution(CreateInstitutionDTO institutionDTO) {
        var laboratory = new Laboratory();
        laboratory.setName(institutionDTO.getName());
        laboratory.setStreet(institutionDTO.getStreet());
        laboratory.setHouseNumber(institutionDTO.getHouseNumber());
        laboratory.setZip(institutionDTO.getZip());
        laboratory.setCity(institutionDTO.getCity());
        laboratory.setEmail(institutionDTO.getEmail());
        laboratory.setPhoneNumber(institutionDTO.getPhoneNumber());
        laboratory.setComment(institutionDTO.getComment());

        return this.laboratoryRepository.saveAndFlush(laboratory);
    }

    @Transactional
    public Clinic createClinicInstitution(CreateInstitutionDTO institutionDTO) {
        var clinic = new Clinic();
        clinic.setName(institutionDTO.getName());
        clinic.setStreet(institutionDTO.getStreet());
        clinic.setHouseNumber(institutionDTO.getHouseNumber());
        clinic.setZip(institutionDTO.getZip());
        clinic.setCity(institutionDTO.getCity());
        clinic.setEmail(institutionDTO.getEmail());
        clinic.setPhoneNumber(institutionDTO.getPhoneNumber());
        clinic.setComment(institutionDTO.getComment());

        return this.clinicRepository.saveAndFlush(clinic);
    }

    @Transactional
    public TestSite createTestSiteInstitution(CreateInstitutionDTO institutionDTO) {
        var testSite = new TestSite();
        testSite.setName(institutionDTO.getName());
        testSite.setStreet(institutionDTO.getStreet());
        testSite.setHouseNumber(institutionDTO.getHouseNumber());
        testSite.setZip(institutionDTO.getZip());
        testSite.setCity(institutionDTO.getCity());
        testSite.setEmail(institutionDTO.getEmail());
        testSite.setPhoneNumber(institutionDTO.getPhoneNumber());
        testSite.setComment(institutionDTO.getComment());

        return this.testSiteRepository.saveAndFlush(testSite);
    }

}
