package org.sharyan.project.cardwebapp.controller;

import org.sharyan.project.cardwebapp.dto.PaymentCardDto;
import org.sharyan.project.cardwebapp.service.PaymentCardService;
import org.sharyan.project.cardwebapp.validation.Patterns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class PaymentCardController {

    @Autowired
    private PaymentCardService paymentCardService;

    @PostMapping("/card/add")
    public String addNewPaymentCard(@Valid PaymentCardDto newCard) {
        return "homepage?addCardSuccess";
    }

    @PostMapping("/card/search")
    public String searchCards(@RequestParam String searchTerm, Errors errors, Model model) {
        if (!Patterns.VALID_SEARCH_CARD_PATTERN.matcher(searchTerm).matches()) {
            errors.rejectValue("searchTerm", "errors.fields.searchTerm");
            return "searchResults";
        } else {
            model.addAttribute("cards", paymentCardService.searchForPaymentCards(searchTerm));
            return "searchResults";
        }
    }
}
