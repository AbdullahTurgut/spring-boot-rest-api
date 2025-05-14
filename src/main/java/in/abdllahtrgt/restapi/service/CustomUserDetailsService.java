package in.abdllahtrgt.restapi.service;

import in.abdllahtrgt.restapi.entity.ProfileEntity;
import in.abdllahtrgt.restapi.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


/**
 * Custom implementation of {@link UserDetailsService} to load user-specific data.
 * <p>
 * This service is used by Spring Security to authenticate users based on their email.
 * It fetches user data from the database using {@link ProfileRepository}.
 * </p>
 *
 * <h4>Responsibilities:</h4>
 * <ul>
 *     <li>Loads a user by email</li>
 *     <li>Returns a {@link User} object required by Spring Security</li>
 *     <li>Throws {@link UsernameNotFoundException} if user not found</li>
 * </ul>
 * <p>
 * Lombok Annotations:
 * <ul>
 *     <li>{@link Service} - Marks the class as a Spring service component</li>
 *     <li>{@link Slf4j} - Enables logging with {@code log.info(...)} etc.</li>
 *     <li>{@link RequiredArgsConstructor} - Generates constructor for final fields</li>
 * </ul>
 *
 * @author Abdullah
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    /**
     * Loads user details from the database using the provided email.
     *
     * @param email the email of the user to load
     * @return a {@link UserDetails} instance with the user's credentials
     * @throws UsernameNotFoundException if no user is found with the given email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ProfileEntity profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Profile not found for the email " + email));
        log.info("Inside loadUserByUsername():: printing the profile details {}", profile);
        return new User(profile.getEmail(), profile.getPassword(), new ArrayList<>());
    }
}
