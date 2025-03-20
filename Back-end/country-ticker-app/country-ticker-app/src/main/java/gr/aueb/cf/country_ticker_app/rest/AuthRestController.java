package gr.aueb.cf.country_ticker_app.rest;

import gr.aueb.cf.country_ticker_app.authentication.AuthenticationService;
import gr.aueb.cf.country_ticker_app.core.exceptions.AppObjectNotAuthorizedException;
import gr.aueb.cf.country_ticker_app.dto.AuthenticationRequestDTO;
import gr.aueb.cf.country_ticker_app.dto.AuthenticationResponseDTO;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for authentication operations.
 * <p>
 * This class handles API requests related to user authentication, such as login.
 * </p>
 *
 * <b>Endpoints:</b>
 * - POST /api/auth/authenticate
 *
 * @author ilias
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRestController.class);
    private final AuthenticationService authenticationService;

    /**
     * Authenticates a user based on the provided credentials.
     *
     * @param authenticationRequestDTO the DTO containing the user's authentication data
     * @return a {@link ResponseEntity} containing the authentication response DTO and HTTP status
     * @throws AppObjectNotAuthorizedException if the user is not authorized
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO authenticationRequestDTO)
                throws AppObjectNotAuthorizedException {
        AuthenticationResponseDTO authenticationResponseDTO = authenticationService.authenticate(authenticationRequestDTO);
        LOGGER.info("User authenticated");
        return new ResponseEntity<>(authenticationResponseDTO, HttpStatus.OK);
    }
}
