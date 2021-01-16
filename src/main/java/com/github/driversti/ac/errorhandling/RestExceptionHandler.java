package com.github.driversti.ac.errorhandling;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.github.driversti.ac.errorhandling.exceptions.ConflictException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
    List<ValidationError> errors;
    if (ex.getBindingResult().hasFieldErrors()) {
      errors = ex.getBindingResult().getFieldErrors().stream()
          .map(this::toValidationError)
          .collect(Collectors.toList());
    } else {
      errors = ex.getBindingResult().getGlobalErrors().stream()
          .map(this::toValidationError)
          .collect(Collectors.toList());
    }
    return createResponse(HttpStatus.BAD_REQUEST, request, "Validation error", errors);
  }

  @ResponseBody
  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<ApiError> handleConstraintViolationException(
      ConstraintViolationException ex, WebRequest request) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @ResponseBody
  @ExceptionHandler({ConflictException.class})
  public ResponseEntity<ApiError> handleConflictException(ConflictException ex,
      WebRequest request) {
    return createResponse(HttpStatus.BAD_REQUEST, request, ex.getMessage());
  }

  private ResponseEntity<ApiError> createResponse(HttpStatus status, WebRequest request,
      String message, List<ValidationError> validationErrors) {
    ApiError apiError = new ApiError(status.value(), message,
        validationErrors);
    return ResponseEntity.status(status).body(apiError);
  }

  private ResponseEntity<ApiError> createResponse(HttpStatus status, WebRequest request,
      String message) {
    return createResponse(status, request, message, null);
  }

  private ValidationError toValidationError(FieldError error) {
    String field = error.getField();
    String message = error.getDefaultMessage();
    Object invalidValue = error.getRejectedValue();
    return new ValidationError(field, message, invalidValue);
  }

  private ValidationError toValidationError(ObjectError error) {
    String message = error.getDefaultMessage();
    return new ValidationError(null, message, null);
  }

  // TODO implement later
  private String traceId(WebRequest request) {
    return request.getHeader("x-request-id");
  }
}
