package org.sharyan.project.cardwebapp;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.sharyan.project.cardwebapp.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SanityTests {


    @Autowired
    private UserController userController;


    @Test
    public void checkContextValid() {
        assertThat(userController).isNotNull();
    }

}
