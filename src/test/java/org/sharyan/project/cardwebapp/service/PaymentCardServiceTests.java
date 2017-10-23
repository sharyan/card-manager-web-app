package org.sharyan.project.cardwebapp.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sharyan.project.cardwebapp.config.ApplicationConfig;
import org.sharyan.project.cardwebapp.dto.CardExpiry;
import org.sharyan.project.cardwebapp.dto.PaymentCardDto;
import org.sharyan.project.cardwebapp.persistence.dao.PaymentCardRepository;
import org.sharyan.project.cardwebapp.persistence.entity.PaymentCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@Import({TestServiceConfig.class, ApplicationConfig.class})
public class PaymentCardServiceTests {

    @Autowired
    private PaymentCardRepository paymentCardRepository;

    @Autowired
    private PaymentCardService paymentCardService;

    @Test
    public void testPaymentCardSearch() {
        String searchTerm = "000-000-000-000-000";
        when(paymentCardRepository.findAllByCardNumberEquals(eq(searchTerm))).thenReturn(Collections.emptyList());
        assertThat(paymentCardService.searchForPaymentCards(searchTerm)).isEmpty();
        verify(paymentCardRepository, times(1)).findAllByCardNumberEquals(eq(searchTerm));
    }

    @Test
    public void testCardAdd() {
        PaymentCardDto paymentCardDto = PaymentCardDto.builder().cardNumber("0000000000000")
                .cardName("Card1")
                .cardExpiry(new CardExpiry("2018-04"))
                .build();

        when(paymentCardRepository.findByCardNumber(eq(paymentCardDto.getCardNumber())))
                .thenReturn(null);

        paymentCardService.addOrUpdatePaymentCard(paymentCardDto);

        verify(paymentCardRepository, never()).deleteById(anyLong());
        verify(paymentCardRepository, times(1)).save(eq(PaymentCard.builder()
                .cardNumber(paymentCardDto.getCardNumber())
                .cardName(paymentCardDto.getCardName())
                .year(paymentCardDto.getCardExpiry().getYear().getValue())
                .month(paymentCardDto.getCardExpiry().getMonth().getValue())
                .build()));
    }

    @Test
    public void testCardUpdate() {
        PaymentCardDto paymentCardDto = PaymentCardDto.builder().cardNumber("0000000000000")
                .cardName("Card1")
                .cardExpiry(new CardExpiry("2018-04"))
                .build();
        PaymentCard paymentCard = PaymentCard.builder()
                .id(1L)
                .cardNumber(paymentCardDto.getCardNumber())
                .build();

        when(paymentCardRepository.findByCardNumber(eq(paymentCard.getCardNumber())))
                .thenReturn(paymentCard);

        paymentCardService.addOrUpdatePaymentCard(paymentCardDto);


        verify(paymentCardRepository, times(1)).deleteById(eq(paymentCard.getId()));
        verify(paymentCardRepository, times(1)).save(eq(PaymentCard.builder()
                .cardNumber(paymentCardDto.getCardNumber())
                .cardName(paymentCardDto.getCardName())
                .year(paymentCardDto.getCardExpiry().getYear().getValue())
                .month(paymentCardDto.getCardExpiry().getMonth().getValue())
                .build()));
    }
}
