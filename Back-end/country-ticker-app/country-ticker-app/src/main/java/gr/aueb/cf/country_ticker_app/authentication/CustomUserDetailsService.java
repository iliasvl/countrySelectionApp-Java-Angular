package gr.aueb.cf.country_ticker_app.authentication;


import gr.aueb.cf.country_ticker_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service implementation for user authentication.
 * <p>
 * This service is used to load user-specific data during the authentication process.
 * It implements the {@link UserDetailsService} interface provided by Spring Security.
 * </p>
 *
 * @author ilias
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Repository for accessing user data.
     * Injected as a dependency through the constructor by Lombok's @RequiredArgsConstructor annotation.
     */
    private final UserRepository userRepository;

    /**
     * Loads a user's details based on the provided username.
     * <p>
     * This method queries the {@link UserRepository} to find the user by username.
     * If no user is found, a {@link UsernameNotFoundException} is thrown.
     * </p>
     *
     * @param username the username of the user to load
     * @return the {@link UserDetails} object containing the user's information
     * @throws UsernameNotFoundException if no user with the provided username is found
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("User with username: " + username + " not found."));
    }
}
