package com.github.driversti.ac.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationError {

  private String field;
  private String message;
  private Object rejectedValue;
}
