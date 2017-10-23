package org.sharyan.project.cardwebapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sharyan.project.cardwebapp.config.ApplicationConfig;
import org.sharyan.project.cardwebapp.config.SecurityConfig;
import org.sharyan.project.cardwebapp.controller.PaymentCardController;
import org.sharyan.project.cardwebapp.persistence.dao.PaymentCardRepository;
import org.sharyan.project.cardwebapp.persistence.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
