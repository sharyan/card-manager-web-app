package org.sharyan.project.cardwebapp.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        // TODO: add login view
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public String performLogin() {
        // TODO: add welcome home page
        return "login";
    }

    @GetMapping("/register")
    public ModelAndView getRegistrationPage() {
        // TODO: add registration view
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView doRegistration() {
        // TODO: add welcome home page
        return new ModelAndView("homepage");
    }
}
