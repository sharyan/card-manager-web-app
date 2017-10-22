package org.sharyan.project.cardwebapp.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
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
public class RegistrationLoginFlowTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testRegistrationAndLogin() {
        String registrationPageContent = restTemplate.getForObject("/register", String.class);

        assertThat(registrationPageContent).contains("Registration");

        UserDto newUser = UserDto.builder().username("john").password("mypassword").build();
        ResponseEntity<String> registrationResult = restTemplate.postForEntity("/register", newUser, String.class);

        assertThat(registrationResult)
                .hasFieldOrPropertyWithValue("getStatusCode", HttpStatus.FOUND);

    }
}
