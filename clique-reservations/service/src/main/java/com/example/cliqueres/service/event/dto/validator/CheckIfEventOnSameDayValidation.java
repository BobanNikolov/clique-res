package com.example.cliqueres.service.event.dto.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckIfEventOnSameDayValidator.class)
public @interface CheckIfEventOnSameDayValidation {
  String message() default "There cannot be more than one events on same day!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
