package org.sharyan.project.cardwebapp.validation;

import org.springframework.lang.Nullable;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(@Nullable Object target, Errors errors) {

    }
}
