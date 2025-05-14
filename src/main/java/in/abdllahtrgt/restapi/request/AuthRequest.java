package in.abdllahtrgt.restapi.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class for handling authentication (login) requests.
 * <p>
 * Contains user credentials: email and password.
 * </p>
 *
 * <ul>
 *     <li>{@code email} - User's email address</li>
 *     <li>{@code password} - User's raw password</li>
 * </ul>
 * <p>
 * Used primarily in controller endpoints like {@code /login}.
 * <p>
 * Lombok Annotations:
 * <ul>
 *     <li>{@link Data} - Generates getters, setters, toString, equals, and hashCode methods</li>
 *     <li>{@link AllArgsConstructor} - Creates a constructor with all fields</li>
 *     <li>{@link NoArgsConstructor} - Creates a no-argument constructor</li>
 *     <li>{@link Builder} - Enables builder pattern for object creation</li>
 * </ul>
 *
 * @author Abdullah
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    private String email;
    private String password;
}
