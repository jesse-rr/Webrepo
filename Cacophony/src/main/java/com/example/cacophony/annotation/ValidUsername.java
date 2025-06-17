package com.example.cacophony.annotation;

import com.example.cacophony.annotation.validator.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {
    String message() default "Invalid username";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
