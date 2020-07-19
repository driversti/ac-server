package com.github.driversti.ac.errorhandling;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.Instant;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ApiError {

  private Instant timestamp;
  private Integer errorCode;
  // todo implement later
//  private String traceId;
  private String message;
  private Collection<ValidationError> validationErrors;

  public ApiError(Integer errorCode, String message,
      Collection<ValidationError> validationErrors) {
    this.timestamp = Instant.now();
    this.errorCode = errorCode;
//    this.traceId = traceId;
    this.message = message;
    this.validationErrors = validationErrors;
  }

  public ApiError(Integer errorCode, String message) {
    this.timestamp = Instant.now();
    this.errorCode = errorCode;
//    this.traceId = traceId;
    this.message = message;
  }
}
