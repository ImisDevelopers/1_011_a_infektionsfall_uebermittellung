package de.coronavirus.imis.api.exception;

import io.swagger.annotations.ApiModel;
import lombok.experimental.Accessors;
import lombok.Data;

/**
 * Exception thrown for operation failures in controllers where additional
 * information for the client is included in the HTTP response.
 */
@Data
public class FailureWithDetailsException extends RuntimeException {
  public static final int DEFAULT_STATUS_CODE = 400;

  private String errorType;
  private int statusCode;
  private Object details;

  public FailureWithDetailsException(String errorType, String message, int statusCode, Object details) {
    super(message);
    this.errorType = errorType;
    this.statusCode = statusCode;
    this.details = details;
  }
  public FailureWithDetailsException(String errorType, int statusCode, Object details) {
    this(errorType, errorType, statusCode, details);
  }
  public FailureWithDetailsException(String errorType, String message, Object details) {
    this(errorType, message, DEFAULT_STATUS_CODE, details);
  }
  public FailureWithDetailsException(String errorType, Object details) {
    this(errorType, DEFAULT_STATUS_CODE, details);
  }

  protected Response createCustomResponse(Object details) {
    return new ObjectResponse(details);
  }

  public Response createResponse() {
    return this.createCustomResponse(this.getDetails())
      .setErrorType(this.getErrorType())
      .setMessage(this.getMessage());
  }

  @Data
  @Accessors(chain = true)
  public static class Response {
    private String errorType;
    private String message;
  }

  @Data
  @ApiModel("ErrorResponse")
  public static class ObjectResponse extends Response {
    private Object details;

    public ObjectResponse(Object details) {
      this.details = details;
    }
  }
}
