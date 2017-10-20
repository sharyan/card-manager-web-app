package org.sharyan.project.cardwebapp.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.security")
public class SecurityProperties {

    private int loginAttemptLimit = 10;

    private Password password;

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public int getLoginAttemptLimit() {
        return loginAttemptLimit;
    }

    public void setLoginAttemptLimit(int loginAttemptLimit) {
        this.loginAttemptLimit = loginAttemptLimit;
    }

    public static class Password {
        private int strength = 11;

        public int getStrength() {
            return strength;
        }

        public void setStrength(int strength) {
            this.strength = strength;
        }
    }
}
