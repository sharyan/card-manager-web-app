package org.sharyan.project.cardwebapp.controller.advice;

import org.sharyan.project.cardwebapp.controller.UserController;
import org.sharyan.project.cardwebapp.error.UserAlreadyExistsException;
import org.sharyan.project.cardwebapp.error.UserNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {UserController.class})
public class ErrorHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserExistsError(UserAlreadyExistsException userAlreadyExists) {
        return "redirect:/register?registrationError";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handlerUserNotFoundError(UserNotFoundException userNotFoundException) {
        return "redirect:/login";
    }
}
