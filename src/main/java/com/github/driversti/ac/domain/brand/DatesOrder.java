package com.github.driversti.ac.domain.brand;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DatesOrderValidator.class)
public @interface DatesOrder {

  String message() default "The start date must be before the end date";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String start();

  String end();

  @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @interface List {

    DatesOrder[] value();
  }
}
