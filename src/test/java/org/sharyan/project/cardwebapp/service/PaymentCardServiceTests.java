package org.sharyan.project.cardwebapp.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sharyan.project.cardwebapp.config.ApplicationConfig;
import org.sharyan.project.cardwebapp.dto.CardExpiry;
import org.sharyan.project.cardwebapp.dto.PaymentCardDto;
import org.sharyan.project.cardwebapp.persistence.dao.PaymentCardRepository;
import org.sharyan.project.cardwebapp.persistence.dao.UserRepository;
import org.sharyan.project.cardwebapp.persistence.entity.PaymentCard;
import org.sharyan.project.cardwebapp.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
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
    private UserRepository userRepository;

    @Autowired
    private PaymentCardService paymentCardService;



    @Test
    @WithMockUser
    public void testPaymentCardSearch() {
        Authentication requestingUserAuth = SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = User.builder().username(requestingUserAuth.getName()).password("password").id(1L).build();

        when(userRepository.findByUsername(eq(loggedInUser.getUsername())))
                .thenReturn(loggedInUser);

        String searchTerm = "000-000-000-000-000";
        when(paymentCardRepository.findAllByCardNumberLike(eq(searchTerm))).thenReturn(Collections.emptyList());

        assertThat(paymentCardService.searchForPaymentCards(requestingUserAuth, searchTerm)).isEmpty();

        verify(paymentCardRepository, never()).findAllByCardNumberLike(anyString());
        verify(paymentCardRepository, times(1)).findAllByCardNumberLikeAndOwnerIdEquals(eq(searchTerm), eq(loggedInUser.getId()));
    }

    @Test
    @WithMockUser
    public void testCardAdd() {
        Authentication requestingUserAuth = SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = User.builder().username(requestingUserAuth.getName()).password("password").id(1L).build();

        when(userRepository.findByUsername(eq(loggedInUser.getUsername())))
                .thenReturn(loggedInUser);

        PaymentCardDto paymentCardDto = PaymentCardDto.builder().cardNumber("0000000000001")
                .cardName("Card1")
                .cardExpiry(new CardExpiry("2018-04"))
                .build();

        when(paymentCardRepository.findByCardNumberAndOwner_Id(eq(paymentCardDto.getCardNumber()), eq(loggedInUser.getId())))
                .thenReturn(null);

        paymentCardService.addOrUpdatePaymentCard(requestingUserAuth, paymentCardDto);

        verify(paymentCardRepository, never()).deleteById(anyLong());
        verify(paymentCardRepository, times(1)).save(eq(PaymentCard.builder()
                .cardNumber(paymentCardDto.getCardNumber())
                .cardName(paymentCardDto.getCardName())
                .year(paymentCardDto.getCardExpiry().getYear().getValue())
                .month(paymentCardDto.getCardExpiry().getMonth().getValue())
                .owner(loggedInUser)
                .build()));
    }

    @Test
    @WithMockUser
    public void testCardUpdate() {
        Authentication requestingUserAuth = SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = User.builder().username(requestingUserAuth.getName()).password("password").id(1L).build();

        when(userRepository.findByUsername(eq(loggedInUser.getUsername())))
                .thenReturn(loggedInUser);

        PaymentCardDto paymentCardDto = PaymentCardDto.builder().cardNumber("0000000000002")
                .cardName("Card1")
                .cardExpiry(new CardExpiry("2018-04"))
                .build();
        PaymentCard paymentCard = PaymentCard.builder()
                .id(1L)
                .cardNumber(paymentCardDto.getCardNumber())
                .build();

        when(paymentCardRepository.findByCardNumberAndOwner_Id(eq(paymentCard.getCardNumber()), eq(loggedInUser.getId())))
                .thenReturn(paymentCard);

        paymentCardService.addOrUpdatePaymentCard(requestingUserAuth, paymentCardDto);

        verify(paymentCardRepository, times(1)).deleteById(eq(paymentCard.getId()));
        verify(paymentCardRepository, times(1)).save(eq(PaymentCard.builder()
                .cardNumber(paymentCardDto.getCardNumber())
                .cardName(paymentCardDto.getCardName())
                .year(paymentCardDto.getCardExpiry().getYear().getValue())
                .month(paymentCardDto.getCardExpiry().getMonth().getValue())
                .owner(loggedInUser)
                .build()));
    }
}
