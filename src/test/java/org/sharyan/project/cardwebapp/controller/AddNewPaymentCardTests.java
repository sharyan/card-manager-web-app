package org.sharyan.project.cardwebapp.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.sharyan.project.cardwebapp.config.ApplicationConfig;
import org.sharyan.project.cardwebapp.config.SecurityConfig;
import org.sharyan.project.cardwebapp.persistence.dao.PaymentCardRepository;
import org.sharyan.project.cardwebapp.persistence.dao.UserRepository;
import org.sharyan.project.cardwebapp.persistence.entity.PaymentCard;
import org.sharyan.project.cardwebapp.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PaymentCardController.class)
@Import({SecurityConfig.class, ApplicationConfig.class})
public class AddNewPaymentCardTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PaymentCardRepository paymentCardRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAddNewCardPageWhenNotLoggedIn() throws Exception {
        mockMvc.perform(get("/card/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(result -> assertThat(result.getResponse().getRedirectedUrl()).contains("login"));
    }

    @Test
    @WithMockUser
    public void testGetAddNewCardPageWithLoggedInUser() throws Exception {
        mockMvc.perform(get("/card/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("addCard"));
    }

    @Test
    public void testAddNewCardWithNoLoggedInUser() throws Exception {
        mockMvc.perform(post("/card/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(result -> assertThat(result.getResponse().getRedirectedUrl()).contains("login"));
    }

    @Test
    @WithMockUser
    public void testAddNewCardWithLoggedInUser() throws Exception {
        String cardNumber = "0000000000000";
        String cardName = "newCard";
        String cardExpiry = "2019-02";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User loggedInUser = User.builder().username(auth.getName())
                .password((String)auth.getCredentials())
                .id(12L)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        when(userRepository.findByUsername(auth.getName())).thenReturn(loggedInUser);

        mockMvc.perform(post("/card/add")
                    .param("cardNumber", cardNumber)
                    .param("cardName", cardName)
                    .param("cardExpiry", cardExpiry)
                    .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/homepage?addCardSuccess"));

        verify(paymentCardRepository, times(1))
    }
}
