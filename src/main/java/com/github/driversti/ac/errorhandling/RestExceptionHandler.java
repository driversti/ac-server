package com.github.driversti.ac.errorhandling;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class RestExceptionHandler {

  @ResponseBody
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<ApiError> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex, WebRequest request) {
    List<ValidationError> validationErrors = ex.getBindingResult().getFieldErrors().stream()
        .map(this::toValidationError)
        .collect(Collectors.toList());
    return createResponse(HttpStatus.BAD_REQUEST, request, "Validation error", validationErrors);
  }

  private ResponseEntity<ApiError> createResponse(HttpStatus status, WebRequest request,
      String cause, List<ValidationError> validationErrors) {
    ApiError apiError = new ApiError(status.value(), cause,
        validationErrors);
    return ResponseEntity.status(status).body(apiError);
  }

  private ValidationError toValidationError(FieldError fieldError) {
    String field = fieldError.getField();
    String message = fieldError.getDefaultMessage();
    Object invalidValue = fieldError.getRejectedValue();
    return new ValidationError(field, message, invalidValue);
  }

  // TODO implement later
  private String traceId(WebRequest request) {
    return request.getHeader("x-request-id");
  }
}
