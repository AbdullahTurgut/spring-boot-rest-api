package in.abdllahtrgt.restapi.config;

import in.abdllahtrgt.restapi.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Configuration class for setting up Spring Security with JWT-based authentication.
 * <p>
 * Defines security filter chain, authentication manager, password encoding,
 * and JWT authentication filter.
 * </p>
 *
 * @author Abdullah
 * @since 1.0
 */
@Configuration
public class WebSecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    /**
     * Defines the main security filter chain.
     *
     * <ul>
     *     <li>Disables CSRF protection (suitable for stateless APIs).</li>
     *     <li>Allows unauthenticated access to login and register endpoints.</li>
     *     <li>Requires authentication for all other requests.</li>
     *     <li>Disables session creation (stateless).</li>
     *     <li>Adds JWT authentication filter before the username/password filter.</li>
     * </ul>
     *
     * @param httpSecurity the {@link HttpSecurity} object to configure security behavior
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if any security configuration error occurs
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable) // csrf->csrf.disable() -> AbstractHttpConfigurer::disable
                .authorizeHttpRequests(auth -> auth.requestMatchers("/login", "/register")
                        .permitAll().anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                //.httpBasic(Customizer.withDefaults())
                .build();
    }

    /**
     * Registers the JWT authentication filter to be used before the standard Spring Security filter.
     *
     * @return instance of {@link JwtRequestFilter}
     */
    @Bean
    public JwtRequestFilter authenticationJwtTokenFilter() {
        return new JwtRequestFilter();
    }

    /**
     * Provides the {@link AuthenticationManager} used for authenticating users.
     *
     * @param authenticationConfiguration the Spring-managed authentication configuration
     * @return the {@link AuthenticationManager} bean
     * @throws Exception if an error occurs retrieving the authentication manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    /**
     * Configures the authentication provider to use custom user details and password encoding.
     *
     * @return the {@link DaoAuthenticationProvider} bean
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Defines the password encoder used for encrypting and verifying passwords.
     *
     * @return the {@link PasswordEncoder} bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
