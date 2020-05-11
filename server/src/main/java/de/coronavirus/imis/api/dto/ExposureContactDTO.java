package de.coronavirus.imis.api.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.services.PatientService;

@Component
public abstract class ExposureContactDTO {
  @Data
  public static class Common {
    private long id;
    private String context;
    private String dateOfContact;
    private String comment;
  }

  @ApiModel("ExposureContactToServer")
  @Data
  public static class ToServer {
    private String source;
    @JsonDeserialize(converter = ExOrNewContactConverter.class)
    private String contact;

    @JsonUnwrapped
    private ExposureContactDTO.Common common;
  }

  @ApiModel("ExposureContactFromServer")
  @Data
  public static class FromServer {
    private ExposureContactDTO.ContactView source;
    private ExposureContactDTO.ContactView contact;

    @JsonUnwrapped
    private ExposureContactDTO.Common common;
  }

  @ApiModel("ExposureContactContactView")
  @Data
  public static class ContactView {
    private String id;
    private String firstName;
    private String lastName;
    private boolean infected;
    private boolean inQuarantine;
  }


  /**
   * Allow exposure contacts to be specified either by ID to
   * an existing patient, or data for a new Patient instance
   */
  @Component
  public static class ExOrNewContactConverter
    extends StdConverter<JsonNode, String> {

    @Autowired
    private PatientService patientService;

    private ObjectMapper jsonMapper = new ObjectMapper();

    public String convert(JsonNode in) {
      if (in == null) {
        return null;
      } else if (in.isTextual()) {
        return in.textValue();
      } else {
        try {
          CreatePatientDTO newPatientDto = this.jsonMapper.treeToValue(in, CreatePatientDTO.class);
          return this.patientService.addPatient(newPatientDto, true).getId();
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }
}
