package ru.codemark.test.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ContrainUpperCaseCharValidator
        implements ConstraintValidator<ContrainUpperCaseChar, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return true;
        }

        return password.matches(".*\\p{Upper}.*");
    }
}
