package org.sharyan.project.cardwebapp.service;

import org.sharyan.project.cardwebapp.dto.PaymentCardDto;
import org.sharyan.project.cardwebapp.persistence.dao.PaymentCardRepository;
import org.sharyan.project.cardwebapp.persistence.entity.PaymentCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class PaymentCardService {

    @Autowired
    private PaymentCardRepository paymentCardRepository;

    @Transactional
    @Secured(value = {"ROLE_USER"})
    public void addOrUpdatePaymentCard(PaymentCardDto paymentCardDto) {
        // check if payment card already exists
        PaymentCard existingPaymentCard = paymentCardRepository.findByCardNumber(paymentCardDto.getCardNumber());

        if (existingPaymentCard != null) {
            // delete card, and update with new details
            paymentCardRepository.deleteById(existingPaymentCard.getId());
        }

        paymentCardRepository.save(PaymentCard.builder()
                .cardNumber(paymentCardDto.getCardNumber())
                .cardName(paymentCardDto.getCardName())
                .month(paymentCardDto.getCardExpiry().getMonth().getValue())
                .year(paymentCardDto.getCardExpiry().getYear().getValue())
                .build());
    }

    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public List<PaymentCard> searchForPaymentCards(String cardNumber) {
        return paymentCardRepository.findAllByCardNumberEquals(cardNumber);
    }
}
