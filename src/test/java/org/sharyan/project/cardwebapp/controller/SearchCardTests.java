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

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PaymentCardController.class)
@Import({SecurityConfig.class, ApplicationConfig.class})
public class SearchCardTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PaymentCardRepository paymentCardRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSearchCardWhenNotLoggedIn() throws Exception {
        mockMvc.perform(get("/card/search"))
                .andExpect(status().is3xxRedirection())
                .andExpect(result -> assertThat(result.getResponse().getRedirectedUrl()).contains("login"));
    }

    @Test
    @WithMockUser
    public void testSearchCardWhenLoggedIn() throws Exception {
        mockMvc.perform(get("/card/search")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("cardSearch"));
    }

    @Test
    public void testSearchWhenNotLoggedIn() throws Exception {
        mockMvc.perform(post("/card/find")
                .param("searchTerm", "SEARCHVALUE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(result -> assertThat(result.getResponse().getRedirectedUrl()).contains("login"));
    }

    @Test
    @WithMockUser
    public void testSearchWhenLoggedIn() throws Exception {
        mockMvc.perform(post("/card/find")
                .param("searchTerm", "SEARCHVALUE")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("searchResults"));
    }

    @Test
    @WithMockUser
    public void testSearchWithResults() throws Exception {
        String searchTerm = "00000000000000";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        when(userRepository.findByUsername(eq(auth.getName())))
                .thenReturn(User.builder().username(auth.getName()).id(1L).build());

        when(paymentCardRepository.findAllByCardNumberLikeAndOwnerIdEquals(searchTerm, 1L))
                .thenReturn(IntStream.of(3).mapToObj(i -> PaymentCard.builder()
                        .cardNumber(searchTerm)
                        .cardName(searchTerm + "-" + i)
                        .month(i)
                        .year(2020 + i)
                        .build()).collect(Collectors.toList()));

        mockMvc.perform(post("/card/find")
                .param("searchTerm", searchTerm)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("searchResults"));
    }
}
