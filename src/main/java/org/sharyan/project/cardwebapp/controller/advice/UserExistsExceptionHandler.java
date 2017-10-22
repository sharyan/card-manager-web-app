package org.sharyan.project.cardwebapp.controller.advice;

import org.sharyan.project.cardwebapp.controller.UserController;
import org.sharyan.project.cardwebapp.error.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice(assignableTypes = {UserController.class})
public class UserExistsExceptionHandler {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserExistsError(UserAlreadyExistsException userAlreadyExists, RedirectAttributes redirectAttributes) {
        return "redirect:/register?registrationError";
    }
}
