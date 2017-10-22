package org.sharyan.project.cardwebapp.validation;

import org.sharyan.project.cardwebapp.dto.UserDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.username.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password.empty");
    }
}