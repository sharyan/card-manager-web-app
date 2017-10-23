package org.sharyan.project.cardwebapp.controller;

import org.sharyan.project.cardwebapp.dto.PaymentCardDto;
import org.sharyan.project.cardwebapp.service.PaymentCardService;
import org.sharyan.project.cardwebapp.validation.Patterns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class PaymentCardController {

    @Autowired
    private PaymentCardService paymentCardService;

    @GetMapping(path = "/card/new")
    public String getAddNewPaymentCardPage(Model model) {
        model.addAttribute("paymentCard", new PaymentCardDto());
        return "addCard";
    }

    @PostMapping("/card/add")
    public String addNewPaymentCard(@ModelAttribute("paymentCard") @Valid PaymentCardDto paymentCard,
                                    BindingResult result,
                                    Authentication auth) {
        if (result.hasErrors()) {
            return "addCard";
        }
        paymentCardService.addOrUpdatePaymentCard(auth, paymentCard);
        return "redirect:/homepage?addCardSuccess";
    }

    @PostMapping("/card/find")
    public String searchCards(@RequestParam String searchTerm, Model model, Authentication auth) {
        if (!Patterns.VALID_SEARCH_CARD_PATTERN.matcher(searchTerm).matches()) {
            model.addAttribute("cards", Collections.emptyList());
            return "searchResults";
        } else {
            model.addAttribute("cards", paymentCardService.searchForPaymentCards(auth, searchTerm));
            return "searchResults";
        }
    }
}
