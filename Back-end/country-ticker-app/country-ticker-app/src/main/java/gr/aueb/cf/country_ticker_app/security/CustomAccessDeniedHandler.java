package gr.aueb.cf.country_ticker_app.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * Custom handler for access denial in the Spring Security framework.
 * <p>
 * This class handles cases where an authenticated user tries to access a resource
 * they do not have permissions for. It responds with a `403 Forbidden` status and
 * provides a JSON response detailing the error.
 * </p>
 *
 * <b>Usage:</b> Configure this handler in the security configuration to customize the
 * response for denied access.
 *
 * @author ilias
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Handles access denial by setting the response status and writing a JSON error message.
     *
     * @param request the HTTP request that triggered the access denial
     * @param response the HTTP response object
     * @param accessDeniedException the exception that occurred
     * @throws IOException if an I/O error occurs while writing the response
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {


        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");


        String json = "{\"code\":\"userNotAuthorized\", \"description\":\"User is not authorized to access this endpoint\"}";
        response.getWriter().write(json);

    }
}
