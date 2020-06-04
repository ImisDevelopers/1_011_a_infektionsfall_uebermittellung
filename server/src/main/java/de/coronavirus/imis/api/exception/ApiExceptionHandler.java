package de.coronavirus.imis.api.exception;

import de.coronavirus.imis.services.incidents.TestIncidentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import org.checkerframework.checker.units.qual.A;
import org.springframework.dao.DataIntegrityViolationException;
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
  ResponseEntity<ExceptionResponse> handle_DataIntegrityViolationException (DataIntegrityViolationException e){
  	return ResponseEntity
			.status(400)
			.body(new ExceptionResponse("CONSTRAINT_VIOLATION"));
  }

  @ExceptionHandler
  ResponseEntity<ExceptionResponse> handle_TestNotFoundException (TestIncidentService.TestNotFoundException e){
  	return ResponseEntity
			.status(400)
			.body(new ExceptionResponse("TEST_NOT_FOUND"));
  }

  @AllArgsConstructor
  @Data
  public class ExceptionResponse {
  	String message;
  }
}
