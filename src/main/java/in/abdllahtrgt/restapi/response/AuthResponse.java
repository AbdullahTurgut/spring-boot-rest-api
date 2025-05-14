package in.abdllahtrgt.restapi.response;


import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * DTO class for handling authentication (login) responses.
 * <p>
 * Contains the JWT token and the authenticated user's email address.
 * </p>
 *
 * <ul>
 *     <li>{@code token} - Generated JWT token after successful login</li>
 *     <li>{@code email} - Authenticated user's email</li>
 * </ul>
 * <p>
 * Lombok Annotations:
 * <ul>
 *     <li>{@link Getter} - Generates getters for all fields</li>
 *     <li>{@link AllArgsConstructor} - Creates a constructor with all fields</li>
 * </ul>
 * <p>
 * This class is usually returned by login endpoints such as {@code /login}.
 *
 * @author Abdullah
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String email;
}
