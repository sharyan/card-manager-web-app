package org.sharyan.project.cardwebapp.security;

import org.sharyan.project.cardwebapp.service.SecurityLoginService;
import org.sharyan.project.cardwebapp.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEventSuccessListener extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private SecurityLoginService securityLoginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);
        securityLoginService.loginSucceededFor(RequestUtils.getRequestIpAddress());
    }
}
