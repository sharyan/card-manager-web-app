package org.sharyan.project.cardwebapp.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sharyan.project.cardwebapp.dto.CardExpiry;
import org.sharyan.project.cardwebapp.dto.PaymentCardDto;
import org.sharyan.project.cardwebapp.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserPaymentCardTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private UserDto loggedInUser;

    @Before
    public void setup() {
        // setup tests with new user
        loggedInUser = UserDto.builder().username("john").password("mypassword").build();
        ResponseEntity<String> registrationResult = restTemplate.postForEntity("/user/register", loggedInUser, String.class);
        assertThat(registrationResult.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

    @Test
    public void addNewCard() {
        PaymentCardDto newCard = PaymentCardDto.builder()
                .cardNumber("0000000000000")
                .cardName("card1")
                .cardExpiry(new CardExpiry("2020-11"))
                .build();

        ResponseEntity<String> result = restTemplate.postForEntity("/card/add", newCard, String.class);

        assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.FOUND);
    }
}
