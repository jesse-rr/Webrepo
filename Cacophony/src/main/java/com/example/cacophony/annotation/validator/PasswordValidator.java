package com.example.cacophony.annotation.validator;

import com.example.cacophony.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null) {
            return false;
        }
        boolean hasDigit = false;
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;
            if (Character.isDigit(c)) hasDigit = true;
            if ("@#$%^&+=".indexOf(c) >= 0) hasSpecial = true;
        }

        return hasDigit && hasLower && hasUpper && hasSpecial;
    }
}
