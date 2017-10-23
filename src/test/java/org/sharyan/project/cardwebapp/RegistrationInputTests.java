package org.sharyan.project.cardwebapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.sharyan.project.cardwebapp.config.ApplicationConfig;
import org.sharyan.project.cardwebapp.config.SecurityConfig;
import org.sharyan.project.cardwebapp.controller.UserController;
import org.sharyan.project.cardwebapp.persistence.dao.PaymentCardRepository;
import org.sharyan.project.cardwebapp.persistence.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(Parameterized.class)
@WebMvcTest(value = UserController.class, secure = false)
@Import({ApplicationConfig.class, SecurityConfig.class})
public class RegistrationInputTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PaymentCardRepository paymentCardRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResourceBundleMessageSource messageSource;

    private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    @Before
    public void setup() throws Exception {
        TestContextManager testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
    }

    private String getExpectedMessage(String errorCode) {
        return messageSource.getMessage(errorCode, null, DEFAULT_LOCALE);
    }

    @Parameterized.Parameters(name = "{index}: username={0}, password={1}")
    public static Collection<Object[]> invalidLoginParameters() {
        return Arrays.asList(
                new Object[] {"", "password", Arrays.asList(
                        "Username cannot be blank",
                        "Username has to be between 3 and 30 characters")},
                new Object[] {"username", "", Arrays.asList(
                        "Password must be greater than 6 characters",
                        "Password cannot be blank")}
        );
    }

    @Parameterized.Parameter
    public String username;

    @Parameterized.Parameter(value = 1)
    public String password;

    @Parameterized.Parameter(value = 2)
    public List<String> violations;

    @Test
    public void testInvalidRegistrationForNewUsers() throws Exception {
        when(userRepository.findByUsername(eq(username))).thenReturn(null);

        mockMvc.perform(get("/login")).andExpect(status().isOk());

        MvcResult result = mockMvc.perform(post("/user/register")
            .param("username", username)
            .param("password", password))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andReturn();

        BindingResult errorBindings = (BindingResult)result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.user");

        assertTrue(errorBindings.hasErrors());
        assertThat(errorBindings.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)).containsAll(violations);
    }
}
