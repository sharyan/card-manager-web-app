package org.sharyan.project.cardwebapp.security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Simple implementation to block malicious users from causing database interactions
 */
@Service
public class SecurityLoginService {

    @Value("${application.security.loginattemptlimit}")
    private int maxLoginAttemptLimit;

    // TODO: back this with redis, will overload memory resources if DDOS by malicious users
    private LoadingCache<String, Integer> loginAttemptCache;

    public SecurityLoginService() {
        loginAttemptCache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(final String ipAddress) {
                        return 0;
                    }
                });
    }

    public void loginSucceededFor(final String ipAddress) {
        loginAttemptCache.invalidate(ipAddress);
    }

    public void loginFailedFor(final String ipAddress) {
        int attempts = 0;
        try {
            attempts = loginAttemptCache.get(ipAddress);
        } catch (final ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        loginAttemptCache.put(ipAddress, attempts);
    }

    public boolean isBlockingUser(final String ipAddress) {
        if (ipAddress.isEmpty()) {
            return false;
        }
        try {
            return loginAttemptCache.get(ipAddress) >= maxLoginAttemptLimit;
        } catch (final ExecutionException e) {
            return false;
        }
    }
}
