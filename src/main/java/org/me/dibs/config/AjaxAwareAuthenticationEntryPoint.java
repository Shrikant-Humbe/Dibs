package org.me.dibs.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.io.IOException;

/**
 * Custom authentication entry point that returns 401 for AJAX/API requests
 * instead of redirecting to the OAuth2 login page.
 *
 * This prevents CORS errors when the frontend makes XHR calls to protected
 * endpoints — the browser would otherwise follow the redirect to Google's
 * OAuth2 endpoint, which doesn't include CORS headers.
 */
public class AjaxAwareAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final AuthenticationEntryPoint defaultEntryPoint;

    public AjaxAwareAuthenticationEntryPoint(String loginUrl) {
        this.defaultEntryPoint = new LoginUrlAuthenticationEntryPoint(loginUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        if (isAjaxRequest(request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
        } else {
            defaultEntryPoint.commence(request, response, authException);
        }
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        String accept = request.getHeader("Accept");
        String origin = request.getHeader("Origin");

        return "XMLHttpRequest".equals(requestedWith)
                || (accept != null && accept.contains("application/json"))
                || origin != null;
    }
}
