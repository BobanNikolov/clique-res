package com.example.cliqueres.service.reservation.dto.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DuplicateNameOfReservationForSameEventValidator.class)
public @interface DuplicateNameOfReservationForSameEventValidation {
  String message() default "There cannot be reservations with same name for one event!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
