package de.coronavirus.imis.api.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class ExposureContactDTO {
  @Data
  public static class Common {
    private Long id;
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

}
