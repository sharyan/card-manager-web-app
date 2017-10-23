package org.sharyan.project.cardwebapp;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.sharyan.project.cardwebapp.config.ApplicationConfig;
import org.sharyan.project.cardwebapp.config.SecurityConfig;
import org.sharyan.project.cardwebapp.controller.UserController;
import org.sharyan.project.cardwebapp.persistence.dao.PaymentCardRepository;
import org.sharyan.project.cardwebapp.persistence.dao.UserRepository;
import org.sharyan.project.cardwebapp.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
@Import(value = {ApplicationConfig.class, SecurityConfig.class})
public class RegistrationConflictTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PaymentCardRepository paymentCardRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @Test
    public void testUsernameConflict() throws Exception {
        final String username = "EXISTING_USER";
        final String password = "password";

        when(userRepository.findByUsername(eq(username)))
                .thenReturn(User.builder()
                        .username(username)
                        .password(password)
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build());

        String redirectedUrl = mockMvc.perform(post("/user/register")
                .param("username", username)
                .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/register?registrationError"))
                .andReturn().getResponse().getRedirectedUrl();

        assertThat(redirectedUrl).isNotNull().isNotEmpty().contains("registrationError");

        mockMvc.perform(get(redirectedUrl))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(result -> assertThat(result.getResponse().getContentAsString())
                        .contains(messageSource.getMessage("errors.user.alreadyRegistered", null, Locale.getDefault())));
    }
}
