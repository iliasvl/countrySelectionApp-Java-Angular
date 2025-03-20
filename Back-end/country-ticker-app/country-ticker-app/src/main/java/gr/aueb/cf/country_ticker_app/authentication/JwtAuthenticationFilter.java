package gr.aueb.cf.country_ticker_app.authentication;

import gr.aueb.cf.country_ticker_app.security.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter for JWT authentication in incoming HTTP requests.
 * <p>
 * This filter intercepts HTTP requests, validates the JWT token from the "Authorization" header,
 * and sets the authentication details in the security context.
 * </p>
 *
 * @author ilias
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Logger instance for logging warnings and errors.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    /**
     * Service for handling JWT-related operations like token validation and subject extraction.
     */
    private final JwtService jwtService;

    /**
     * Service for loading user details by username.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Filters each HTTP request to validate the JWT token and set authentication details in the security context.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain to proceed with the next filter
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String jwt;
        String username;

        // Check if Authorization header is missing or does not start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);

        try {
            // Extract username from the JWT token
            username = jwtService.extractSubject(jwt);

            // Validate the token and set authentication details
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }else {
                    LOGGER.warn("Token is not valid" + request.getRequestURI());
                }
            }

        } catch (ExpiredJwtException e) {
            LOGGER.warn("WARN: Token is expired", e);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            String jsonBody = "{\"code\":\"Token is expired\", \"message\"" + e.getMessage() + "\"}";
            response.getWriter().write(jsonBody);
            return;

        } catch (Exception e) {
            LOGGER.warn("WARN: Something went wrong while parsing JWT ", e);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            String jsonBody = "{\"code\":\"Token is invalid\", \"description\"" + e.getMessage() + "\"}";
            response.getWriter().write(jsonBody);
            return;
        }

        // Proceed with the filter chain
        filterChain.doFilter(request, response);

    }
}
