package org.sharyan.project.cardwebapp.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user/login")
    public String getLoginPage() {
        // TODO: add login view
        return "login";
    }

    @PostMapping("/user/login")
    public String performLogin() {
        // TODO: add welcome home page
        return "welcome";
    }

    @GetMapping("/user/register")
    public String getRegistrationPage() {
        // TODO: add registration view
        return "register";
    }

    @PostMapping("/user/register")
    public String doRegistration() {
        // TODO: add welcome home page
        return "welcome";
    }
}
