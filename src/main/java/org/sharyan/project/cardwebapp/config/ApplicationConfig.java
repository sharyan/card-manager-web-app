package org.sharyan.project.cardwebapp.config;

import org.sharyan.project.cardwebapp.service.PaymentCardService;
import org.sharyan.project.cardwebapp.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public PaymentCardService paymentCardService() {
        return new PaymentCardService();
    }
}
