package in.abdllahtrgt.restapi.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for managing and validating JSON Web Tokens (JWT).
 * <p>
 * Responsibilities:
 * <ul>
 *     <li>Generate JWT tokens with claims</li>
 *     <li>Extract information (e.g., username, expiration date) from JWT</li>
 *     <li>Validate token expiration and identity</li>
 * </ul>
 * <p>
 * Uses `io.jsonwebtoken` library for all JWT operations.
 * </p>
 *
 * <h4>Configuration:</h4>
 * Reads secret key from `application.properties` using `@Value("${jwt.secret}")`.
 * <p>
 * Example:
 * <pre>{@code
 * jwt.secret=my-super-secret-key
 * }</pre>
 *
 * @author Abdullah
 * @since 1.0
 */
@Component
public class JwtTokenUtil {

    /**
     * Token validity in seconds (5 hours).
     */
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    /**
     * Secret key used to sign JWT, injected from application properties.
     */
    @Value("${jwt.secret}")
    private String secret;


    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails user details (e.g., username/email)
     * @return signed JWT as a string
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }


    /**
     * Extracts the username (email) from the JWT token.
     *
     * @param jwtToken the JWT token
     * @return username (subject) from the token
     */
    public String getUsernameFromToken(String jwtToken) {
        return getClaimFromToken(jwtToken, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the token using a resolver function.
     *
     * @param token         the JWT token
     * @param claimResolver a function to resolve a claim from {@link Claims}
     * @param <T>           type of the claim
     * @return resolved claim
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        final Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claimResolver.apply(claims);
    }

    /**
     * Validates token's username and checks if it is expired.
     *
     * @param jwtToken    the JWT token
     * @param userDetails user details for comparison
     * @return true if token is valid and matches the user, false otherwise
     */
    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        final String email = getUsernameFromToken(jwtToken);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    /**
     * Checks if the token is expired.
     *
     * @param jwtToken the JWT token
     * @return true if token is expired, false otherwise
     */
    private boolean isTokenExpired(String jwtToken) {
        final Date expiration = getClaimFromToken(jwtToken, Claims::getExpiration);
        return expiration.before(new Date());
    }


}
