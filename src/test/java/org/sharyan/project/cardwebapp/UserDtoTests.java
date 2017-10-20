package org.sharyan.project.cardwebapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sharyan.project.cardwebapp.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
@RunWith(SpringRunner.class)
public class UserDtoTests {

    @Autowired
    private JacksonTester<UserDto> userDtoJacksonTester;

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "BIGPASSWORD";
    private static final String JSON_TO_DESERIALIZE = "{\"username\": \""+ USERNAME + "\", \"password\": \"" + PASSWORD + "\"}";
    private UserDto userDto;

    @Before
    public void setup() {
        userDto = UserDto.builder().username(USERNAME).password(PASSWORD).build();
    }

    @Test
    public void usernameSerialises() throws IOException {
        assertThat(this.userDtoJacksonTester.write(userDto)).extractingJsonPathStringValue("@.username").isEqualTo(USERNAME);
    }

    @Test
    public void passwordSerialises() throws IOException {
        assertThat(this.userDtoJacksonTester.write(userDto)).extractingJsonPathStringValue("@.password").isEqualTo(PASSWORD);
    }

    @Test
    public void usernameDeserialises() throws IOException {
        assertThat(this.userDtoJacksonTester.parseObject(JSON_TO_DESERIALIZE).getUsername()).isEqualTo(USERNAME);
    }

    @Test
    public void passworDeserialises() throws IOException {
        assertThat(this.userDtoJacksonTester.parseObject(JSON_TO_DESERIALIZE).getPassword()).isEqualTo(PASSWORD);
    }
}