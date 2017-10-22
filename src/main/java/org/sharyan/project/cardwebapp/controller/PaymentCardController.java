package org.sharyan.project.cardwebapp.controller;

import org.sharyan.project.cardwebapp.dto.CardDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class PaymentCardController {

    @PostMapping("/card/add")
    public String addNewPaymentCard(@Valid CardDto newCard) {
        return "homepage?addCardSuccess";
    }

    @PostMapping("/card/search")
    public String searchCards(@RequestParam String searchTerm, Model model) {
        return "searchResults";
    }
}
