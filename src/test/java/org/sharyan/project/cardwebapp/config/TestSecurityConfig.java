package org.sharyan.project.cardwebapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@TestConfiguration
@EnableConfigurationProperties(value = {TestSecurityProperties.class})
public class TestSecurityConfig extends SecurityConfig {

    @Autowired
    private TestSecurityProperties testSecurityProperties;

    @Override
    public void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
        // @formatter:off
        authBuilder.inMemoryAuthentication()
                    .withUser(testSecurityProperties.getAdmin().getUsername())
                    .password(testSecurityProperties.getAdmin().getPassword())
                    .roles("ROLE_ADMIN", "ROLE_USER")
                .and()
                    .withUser(testSecurityProperties.getUser().getUsername())
                    .password(testSecurityProperties.getUser().getPassword())
                    .roles("ROLE_USER");
        // @formatter:on
    }
}
