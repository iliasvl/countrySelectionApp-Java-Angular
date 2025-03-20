package gr.aueb.cf.country_ticker_app.authentication;

import gr.aueb.cf.country_ticker_app.core.exceptions.AppObjectNotAuthorizedException;
import gr.aueb.cf.country_ticker_app.dto.AuthenticationRequestDTO;
import gr.aueb.cf.country_ticker_app.dto.AuthenticationResponseDTO;
import gr.aueb.cf.country_ticker_app.model.User;
import gr.aueb.cf.country_ticker_app.repository.UserRepository;
import gr.aueb.cf.country_ticker_app.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling authentication operations.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    /**
     * Authenticates a user based on the provided credentials and generates a JWT token.
     *
     * @param dto The authentication request containing username and password.
     * @return An {@link AuthenticationResponseDTO} containing user details and JWT token.
     * @throws AppObjectNotAuthorizedException If authentication fails or the user is not found.
     */
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO dto)
            throws AppObjectNotAuthorizedException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new AppObjectNotAuthorizedException("User", "User with username: " + authentication.getName() + " not authorized."));

        String token = jwtService.generateToken(authentication.getName(), user.getRole().name());
        return new AuthenticationResponseDTO(user.getFirstname(), user.getLastname(), token, user.getId());
    }
}
