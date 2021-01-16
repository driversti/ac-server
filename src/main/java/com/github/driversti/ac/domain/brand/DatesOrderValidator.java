package com.github.driversti.ac.domain.brand;

import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.ConfigurablePropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

public class DatesOrderValidator implements ConstraintValidator<DatesOrder, Object> {

  private String start;
  private String end;

  @Override
  public void initialize(DatesOrder annotation) {
    this.start = annotation.start();
    this.end = annotation.end();
  }

  @Override
  public boolean isValid(Object o, ConstraintValidatorContext ctx) {
    final ConfigurablePropertyAccessor accessor = PropertyAccessorFactory
        .forDirectFieldAccess(o);
    final LocalDate startDate = (LocalDate) accessor.getPropertyValue(start);
    final LocalDate endDate = (LocalDate) accessor.getPropertyValue(end);
    if (startDate == null || endDate == null) {
      return false;
    }
    return startDate.isBefore(endDate);
  }
}
