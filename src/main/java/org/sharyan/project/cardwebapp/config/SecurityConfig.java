package org.sharyan.project.cardwebapp.config;


import org.sharyan.project.cardwebapp.config.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.StaticResourceRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests()
                .requestMatchers(StaticResourceRequest.toCommonLocations())
                    .permitAll()
                .mvcMatchers("/login", "/register")
                    .permitAll()
                .anyRequest()
                    .fullyAuthenticated()
            .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/homepage")
                    .failureUrl("/login?error")
                    .permitAll()
            .and()
                .sessionManagement()
                .invalidSessionUrl("/login?expired")
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry())
            .and()
                .sessionFixation().none()
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(false)
                .deleteCookies("JSESSIONID")
                .permitAll();
        // @formatter:on
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth.inMemoryAuthentication()
                    .withUser("admin")
                    .password("admin")
                    .roles("ADMIN", "USER")
                .and()
                    .withUser("user")
                    .password("user")
                    .roles("USER");
        // @formatter:on
    }

    @Bean
    public PasswordEncoder encoder(@Autowired SecurityProperties securityProperties) {
        return new BCryptPasswordEncoder(securityProperties.getPassword().getStrength(), new SecureRandom());
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}
