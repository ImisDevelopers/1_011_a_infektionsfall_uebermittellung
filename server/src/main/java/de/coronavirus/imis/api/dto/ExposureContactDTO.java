package de.coronavirus.imis.api.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import de.coronavirus.imis.domain.Patient;

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
    private String contact;

    @JsonUnwrapped
    private ExposureContactDTO.Common common;
  }

  @ApiModel("ExposureContactFromServer")
  @Data
  public static class FromServer {
    private Patient.IdentityView source;
    private Patient.IdentityView contact;

    @JsonUnwrapped
    private ExposureContactDTO.Common common;
  }
}
