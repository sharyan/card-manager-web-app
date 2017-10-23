package org.sharyan.project.cardwebapp.controller;

import org.sharyan.project.cardwebapp.persistence.dao.PaymentCardRepository;
import org.sharyan.project.cardwebapp.persistence.dao.UserRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class TestControllerConfig {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PaymentCardRepository paymentCardRepository;
}
