package de.coronavirus.imis.api.exception;

import lombok.Data;
import lombok.Builder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller Exception handler that generates appropriate responses for
 * FailureWithDetailsException instances.
 *
 * Note that currently no matching Swagger API documentation is generated.
 * Consequently, the exception's response data model is not visible to the
 * client except if explicitly specified using a Swagger @ApiResponse
 * annotation on the handler method.
 */
@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler
  ResponseEntity<FailureWithDetailsException.Response> handleFailuresWithDetails(FailureWithDetailsException e) {
    return ResponseEntity
      .status(e.getStatusCode())
      .contentType(MediaType.APPLICATION_PROBLEM_JSON)
      .body(e.createResponse());
  }
}
