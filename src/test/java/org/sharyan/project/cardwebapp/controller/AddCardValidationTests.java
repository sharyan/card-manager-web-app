package org.sharyan.project.cardwebapp.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.sharyan.project.cardwebapp.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(Parameterized.class)
@WebMvcTest(value = PaymentCardController.class, secure = false)
@Import({ApplicationConfig.class, TestControllerConfig.class})
public class AddCardValidationTests {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        TestContextManager testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
    }

    @Parameterized.Parameters(name = "{index}: cardNumber={0}, cardName={1}, expiryDate={2}")
    public static Collection<Object[]> invalidLoginParameters() {
        return Arrays.asList(
                new Object[] {"", "card1", "2018-01", Arrays.asList(
                        "Card number cannot be empty",
                        "Card number must only contain numbers")},
                new Object[] {"$%^&*", "card1", "2018-01", Arrays.asList(
                        "Card number must only contain numbers")}
        );
    }

    @Parameterized.Parameter
    public String cardNumber;

    @Parameterized.Parameter(value = 1)
    public String cardName;

    @Parameterized.Parameter(value = 2)
    public String expiryDate;

    @Parameterized.Parameter(value = 3)
    public List<String> violations;

    @Test
    public void testInvalidRegistrationForNewUsers() throws Exception {
        MvcResult result = mockMvc.perform(post("/card/add")
                .param("cardNumber", cardNumber)
                .param("cardName", cardName)
                .param("cardExpiry", expiryDate)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("addCard"))
                .andReturn();

        BindingResult errorBindings = (BindingResult)result.getModelAndView().getModel().get("org.springframework.validation.BindingResult.paymentCard");

        assertTrue(errorBindings.hasErrors());
        assertThat(errorBindings.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)).containsAll(violations);
    }
}
