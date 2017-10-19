package org.sharyan.project.cardwebapp.config;


import org.sharyan.project.cardwebapp.config.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.StaticResourceRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    @Qualifier("persistentUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

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
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler)
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
    public void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
        // @formatter:off
        authBuilder.authenticationProvider(authenticationProvider());
//        authBuilder.inMemoryAuthentication()
//                    .withUser("admin")
//                    .password("admin")
//                    .roles("ADMIN", "USER")
//                .and()
//                    .withUser("user")
//                    .password("user")
//                    .roles("USER");
        // @formatter:on
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(encoder());
        // daoAuthenticationProvider.setUserCache();
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(securityProperties.getPassword().getStrength(), new SecureRandom());
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}
