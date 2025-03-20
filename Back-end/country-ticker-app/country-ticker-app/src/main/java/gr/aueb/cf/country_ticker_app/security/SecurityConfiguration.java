package gr.aueb.cf.country_ticker_app.security;

import gr.aueb.cf.country_ticker_app.authentication.JwtAuthenticationFilter;
import gr.aueb.cf.country_ticker_app.core.enums.Role;
import gr.aueb.cf.country_ticker_app.model.Traveller;
import gr.aueb.cf.country_ticker_app.model.User;
import gr.aueb.cf.country_ticker_app.repository.TravellerRepository;
import gr.aueb.cf.country_ticker_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



import java.util.List;
import java.util.Optional;

/**
 * Security configuration for the application.
 * <p>
 * This class configures the security mechanisms, such as JWT authentication, authorization rules,
 * password encoding, CORS policies, and exception handling.
 * </p>
 *
 * <b>Features:</b>
 * - Stateless JWT-based authentication.
 * - Role-based access control for endpoints.
 * - CORS configuration for cross-origin requests.
 * - Custom handlers for authentication and access denial.
 *
 * @see JwtAuthenticationFilter
 * @see UserDetailsService
 * @see PasswordEncoder
 * @see AuthenticationManager
 * @see AuthenticationProvider
 *
 * @author ilias
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;  //No need to use CustomUserDetailsService as by using directly the UserDetailsService we actually use the CustomUserDetailsService as it is the only one that implements the UserDetailsService.
    private final TravellerRepository travellerRepository;
    private final UserRepository userRepository;


    /**
     * Configures the main security filter chain for the application.
     * <p>
     * This includes:
     * - Stateless session management.
     * - Configuring CORS and CSRF settings.
     * - Defining access rules for various endpoints.
     * - Adding the custom JWT authentication filter.
     * </p>
     *
     * @param http the {@link HttpSecurity} object to configure
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(myCustomAuthenticationEntryPoint()))
                .exceptionHandling(exceptions -> exceptions.accessDeniedHandler(myCustomAccessDeniedHandler()))
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/api/travellers/save").permitAll()   // so everyone should be able to register
                        .requestMatchers("/api/auth/authenticate").permitAll()

                        // Allow travellers to modify their own countries
                        .requestMatchers("/api/travellers/{travellerId}/add",
                                "/api/travellers/{travellerId}/remove-country/{countryId}")
                        .access((authentication, context) -> new AuthorizationDecision(isTravellerOrAdmin(authentication.get().getName(), context.getVariables().get("travellerId"))))



                        // Allow access to total traveled/untraveled country counts
                        .requestMatchers("/api/travellers/{travellerId}/total-travelled-countries",
                                "/api/travellers/{travellerId}/total-untravelled-countries")
                        .access((authentication, context) -> new AuthorizationDecision(isTravellerOrAdmin(authentication.get().getName(), context.getVariables().get("travellerId"))))


                        // Allow travellers to access their own profile
                        .requestMatchers("/api/travellers/{travellerId}")
                        .access((authentication, context) -> {
                            String username = authentication.get().getName();
                            String travellerId = context.getVariables().get("travellerId");

                            // Allow access if the user is an admin OR passes the traveller-specific check
                            boolean isAdmin = authentication.get().getAuthorities().stream()
                                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));

                            return new AuthorizationDecision(isAdmin || isTravellerOrAdmin(username, travellerId));
                        })



                        // Admin-only routes
                        .requestMatchers("/api/travellers").hasAnyAuthority(Role.ADMIN.name())
                        // .requestMatchers("/api/travellers/paginated").hasAnyAuthority(Role.ADMIN.name())
                        // .requestMatchers("/api/travellers/all").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/travellers/all/paginated").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/travellers/{id}/deactivate").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/travellers/{id}/restore").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/travellers/{travellerId}/delete").hasAnyAuthority(Role.ADMIN.name())


                        // anything else
                        .requestMatchers("/**").permitAll()
                )
                .sessionManagement((session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    /**
     * Configures CORS to allow cross-origin requests from specific domains.
     *
     * @return a {@link CorsConfigurationSource} with the CORS settings
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("https://coding-factory.apps.gov.gr",
                "https://test-coding-factory.apps.gov.gr",
                "http://localhost:4200", "http://localhost:5173"));  // ----URL της Angular----
//        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }


    /**
     * Configures the DAO authentication provider for user authentication.
     * <p>
     * This provider uses the custom {@link UserDetailsService} and a BCrypt password encoder.
     * </p>
     *
     * @return the configured {@link AuthenticationProvider}
     */
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Checks if the authenticated user is a traveller or an admin.
     * <p>
     * This helper method ensures that only the owner of the traveller's profile
     * or an admin can access certain routes.
     * </p>
     *
     * @param username    the username of the authenticated user
     * @param travellerId the ID of the traveller to check
     * @return true if the user is authorized, false otherwise
     */
    private boolean isTravellerOrAdmin(String username, String travellerId) {
        Optional<Traveller> travellerOpt = travellerRepository.findById(Long.valueOf(travellerId));
        if (travellerOpt.isPresent()) {
            Traveller traveller = travellerOpt.get();
            return traveller.getUser().getUsername().equals(username) ||
                    getAuthenticatedUserRole(username) == Role.ADMIN;
        }
        return false;
    }

    /**
     * Retrieves the role of the authenticated user.
     *
     * @param username the username of the authenticated user
     * @return the {@link Role} of the user, or null if not found
     */
    private Role getAuthenticatedUserRole(String username) {
        // Retrieve the authenticated user from the database
        return userRepository.findByUsername(username).map(User::getRole).orElse(null);
    }


    /**
     * Configures a BCrypt password encoder.
     *
     * @return a {@link PasswordEncoder} instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    /**
     * Retrieves the authentication manager from the provided configuration.
     *
     * @param config the {@link AuthenticationConfiguration} object
     * @return the {@link AuthenticationManager} instance
     * @throws Exception if an error occurs during retrieval
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    /**
     * Configures the custom authentication entry point for handling unauthorized access.
     *
     * @return a {@link AuthenticationEntryPoint} instance
     */
    @Bean
    public AuthenticationEntryPoint myCustomAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    /**
     * Configures the custom access denied handler for handling forbidden access.
     *
     * @return an {@link AccessDeniedHandler} instance
     */
    @Bean
    public AccessDeniedHandler myCustomAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}


