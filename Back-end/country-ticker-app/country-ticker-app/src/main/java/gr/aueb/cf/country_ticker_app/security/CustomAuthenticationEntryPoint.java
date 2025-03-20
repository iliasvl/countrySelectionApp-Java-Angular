package gr.aueb.cf.country_ticker_app.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * Custom entry point for handling unauthorized access in the Spring Security framework.
 * <p>
 * This class handles cases where an unauthenticated user tries to access a protected resource.
 * It responds with a `401 Unauthorized` status and provides a JSON response prompting the user to authenticate.
 * </p>
 *
 * <b>Usage:</b> Configure this entry point in the security configuration to customize the
 * response for authentication failures.
 *
 * @author ilias
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Handles unauthorized access by setting the response status and writing a JSON error message.
     *
     * @param request the HTTP request that triggered the authentication failure
     * @param response the HTTP response object
     * @param authException the exception that occurred
     * @throws IOException if an I/O error occurs while writing the response
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String json = "{\"code\":\"userNotAuthenticated\", \"description\":\"User must authenticate in order to access this endpoint\"}";
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

    }
}
