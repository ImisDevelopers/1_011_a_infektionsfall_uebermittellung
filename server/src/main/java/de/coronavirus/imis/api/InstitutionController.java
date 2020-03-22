package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.CreateInstitutionDTO;
import de.coronavirus.imis.api.dto.InstitutionDTO;
import de.coronavirus.imis.services.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/institutions")
@RequiredArgsConstructor
public class InstitutionController {

    final InstitutionService institutionService;

    @PostMapping
    public ResponseEntity<InstitutionDTO> createInstitution(@RequestBody CreateInstitutionDTO createInstitutionDTO) {

        var builder = InstitutionDTO.builder();

        var type = createInstitutionDTO.getInstitutionType();

        switch (type) {
            case LABORATORY:
                var laboratory = this.institutionService.createLaboratoryInstitution(createInstitutionDTO);
                builder = builder
                        .id(laboratory.getId())
                        .institutionType(type)
                        .name(laboratory.getName())
                        .street(laboratory.getStreet())
                        .houseNumber(laboratory.getHouseNumber())
                        .zip(laboratory.getZip())
                        .city(laboratory.getCity())
                        .email(laboratory.getEmail())
                        .phoneNumber(laboratory.getPhoneNumber())
                        .comment(laboratory.getComment());
                break;
            case TEST_SITE:
                var testSite = this.institutionService.createTestSiteInstitution(createInstitutionDTO);
                builder = builder
                        .id(testSite.getId())
                        .institutionType(type)
                        .name(testSite.getName())
                        .street(testSite.getStreet())
                        .houseNumber(testSite.getHouseNumber())
                        .zip(testSite.getZip())
                        .city(testSite.getCity())
                        .email(testSite.getEmail())
                        .phoneNumber(testSite.getPhoneNumber())
                        .comment(testSite.getComment());
                break;
            case CLINIC:
                var clinic = this.institutionService.createClinicInstitution(createInstitutionDTO);
                builder = builder
                        .id(clinic.getId())
                        .institutionType(type)
                        .name(clinic.getName())
                        .street(clinic.getStreet())
                        .houseNumber(clinic.getHouseNumber())
                        .zip(clinic.getZip())
                        .city(clinic.getCity())
                        .email(clinic.getEmail())
                        .phoneNumber(clinic.getPhoneNumber())
                        .comment(clinic.getComment());
                break;
            case DOCTORS_OFFICE:
                var doctor = this.institutionService.createDoctorInstitution(createInstitutionDTO);
                builder = builder
                        .id(doctor.getId())
                        .institutionType(type)
                        .name(doctor.getName())
                        .street(doctor.getStreet())
                        .houseNumber(doctor.getHouseNumber())
                        .zip(doctor.getZip())
                        .city(doctor.getCity())
                        .email(doctor.getEmail())
                        .phoneNumber(doctor.getPhoneNumber())
                        .comment(doctor.getComment());
                break;
        }


        return ResponseEntity.ok(builder.build());
    }
}
