package gr.aueb.cf.country_ticker_app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

/**
 * Service for handling JSON Web Tokens (JWTs).
 * <p>
 * This class provides methods to generate, validate, and extract claims from JWT tokens.
 * It uses the HS256 algorithm and a secret key for signing the tokens.
 * </p>
 *
 * <b>Usage:</b> Use this service to issue and verify tokens for user authentication in a Spring Security context.
 *
 * @author ilias
 */
@Service
public class JwtService {


    private String secretKey = "591b754bd20d8a4fc975b8f5d04a1445d0e1861399cbe41e1711c55a47dce68a";
    private long jwtExpiration = 10800000;  // 3 hours in milliseconds

    /**
     * Generates a JWT token for a given username and role.
     *
     * @param username the subject of the token (typically the user's username)
     * @param role the role of the user (e.g., ADMIN, TRAVELLER)
     * @return the generated JWT token as a String
     */
    public String generateToken(String username, String role) {

        var claims = new HashMap<String, Object>();
        claims.put("role", role);
        return Jwts
                .builder()
                .setIssuer("self")
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates a JWT token by checking its subject and expiration.
     *
     * @param token the JWT token to validate
     * @param userDetails the user details to validate the token against
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String subject = extractSubject(token);
        return (subject.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


    public String getStringClaim(String token, String claim) {
        return extractAllClaims(token).get(claim, String.class);
    }

    /**
     * Extracts the subject (username) from the JWT token.
     *
     * @param token the JWT token
     * @return the subject of the token
     */
    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param <T> the type of the claim
     * @param token the JWT token
     * @param claimsResolver a function to resolve the claim
     * @return the resolved claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token the JWT token
     * @return the expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token the JWT token
     * @return the {@link Claims} object containing all claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Creates a HS256 Key. Key is an interface.
     * Starting from secretKey we get a byte array
     * of the secret. Then we get the {@link javax.crypto.SecretKey,
     * class that implements the {@link Key } interface.
     *
     *
     * @return  a SecretKey which implements Key.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
