package org.sharyan.project.cardwebapp.controller.advice;

import org.sharyan.project.cardwebapp.controller.UserController;
import org.sharyan.project.cardwebapp.error.UserAlreadyExistsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {UserController.class})
public class UserExistsExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserExistsError(UserAlreadyExistsException userAlreadyExists) {
        return "redirect:/register?registrationError";
    }
}
