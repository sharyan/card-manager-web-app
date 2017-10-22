package org.sharyan.project.cardwebapp.controller;


import org.sharyan.project.cardwebapp.dto.UserDto;
import org.sharyan.project.cardwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getHomepage() {
        // TODO: add login view
        return "homepage";
    }

    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping(value = "/user/register")
    public String doRegistration(@ModelAttribute @Valid UserDto user, BindingResult result) {
        userService.registerNewUser(user);
        return "homepage";
    }
}
