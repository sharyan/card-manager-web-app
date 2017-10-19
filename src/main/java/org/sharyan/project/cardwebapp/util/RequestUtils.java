package org.sharyan.project.cardwebapp.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

    private RequestUtils() {
    }

    public static String getRequestIpAddress() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            final String forwardForHeader = request.getHeader("X-Forwarded-For");
            if (forwardForHeader == null) {
                return request.getRemoteAddr();
            }
            return forwardForHeader.split(",")[0];
        }
        return "";
    }
}
