package com.example.cacophony.annotation.validator;

import com.example.cacophony.annotation.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null) {
            return false;
        }
        return username.matches("^[a-zA-Z0-9_]+$");
    }
}
