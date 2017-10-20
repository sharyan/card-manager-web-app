package org.sharyan.project.cardwebapp.controller;


import org.sharyan.project.cardwebapp.dto.UserDto;
import org.sharyan.project.cardwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ModelAndView getHomepage() {
        // TODO: add login view
        return new ModelAndView("homepage");
    }

    @GetMapping("/register")
    public ModelAndView getRegistrationPage(Model model) {
        model.addAttribute("user", new UserDto());
        return new ModelAndView("register");
    }

    @PostMapping(value = "/register")
    public ModelAndView doRegistration(@Valid UserDto userDto, BindingResult result) {
        userService.registerNewUser(userDto);
        return new ModelAndView("homepage");
    }
}
