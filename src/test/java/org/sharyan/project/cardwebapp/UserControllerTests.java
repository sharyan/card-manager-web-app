package org.sharyan.project.cardwebapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sharyan.project.cardwebapp.config.ApplicationConfig;
import org.sharyan.project.cardwebapp.config.SecurityConfig;
import org.sharyan.project.cardwebapp.controller.UserController;
import org.sharyan.project.cardwebapp.persistence.dao.UserRepository;
import org.sharyan.project.cardwebapp.persistence.entity.Role;
import org.sharyan.project.cardwebapp.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
@Import({SecurityConfig.class, ApplicationConfig.class})
public class UserControllerTests {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testGetRegistrationPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    @WithMockUser(username = "normalUser", password = "pwdmypass")
    public void testUserLogin() throws Exception {
        FormLoginRequestBuilder loginRequest = formLogin()
                    .user("normalUser")
                    .password("pwdmypass");

        when(userRepository.findByUsername(eq("normalUser"))).thenReturn(User.builder()
                .username("normalUser")
                .password(passwordEncoder.encode("pwdmypass"))
                .roles(Collections.singletonList(Role.builder().name("ROLE_USER").build()))
                .build());

        mockMvc.perform(loginRequest)
                .andExpect(authenticated().withRoles("USER"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/"));
    }

    @Test
    public void testUserControllerBeanValid() {
        // Since we're a @SpringBootTest all beans should be available.
        assertThat(this.applicationContext.getBean(UserController.class))
                .isNotNull();
    }

}
