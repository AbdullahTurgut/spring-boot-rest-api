package in.abdllahtrgt.restapi.controller;

import in.abdllahtrgt.restapi.dto.ProfileDTO;
import in.abdllahtrgt.restapi.request.AuthRequest;
import in.abdllahtrgt.restapi.request.ProfileRequest;
import in.abdllahtrgt.restapi.response.AuthResponse;
import in.abdllahtrgt.restapi.response.ProfileResponse;
import in.abdllahtrgt.restapi.service.CustomUserDetailsService;
import in.abdllahtrgt.restapi.service.IProfileService;
import in.abdllahtrgt.restapi.service.ITokenBlacklistService;
import in.abdllahtrgt.restapi.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * It will save the user to db
 *
 * @author Abdullah
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final ModelMapper modelMapper;
    private final IProfileService profileService;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;
    private final ITokenBlacklistService tokenBlacklistService;

    /**
     * API end point to register new user
     *
     * @param profileRequest
     * @return ProfileResponse
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ProfileResponse createProfile(@Valid @RequestBody ProfileRequest profileRequest) {
        log.info("API /register is called {}", profileRequest);
        ProfileDTO profileDTO = mapToProfileDTO(profileRequest);
        profileDTO = profileService.createProfile(profileDTO);
        log.info("printing the profile dto details {}", profileDTO);
        return mapToProfileResponse(profileDTO);
    }

    @PostMapping("/login")
    public AuthResponse authenticateProfile(@RequestBody AuthRequest authRequest) throws Exception {
        log.info("API /login is called {}", authRequest);
        authenticate(authRequest);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new AuthResponse(token, authRequest.getEmail());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT) // return back -204- code
    @PostMapping("/signout")
    public void signout(HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromRequest(request);
        if (jwtToken != null) {
            tokenBlacklistService.addTokenToBlacklist(jwtToken);
        }
    }

    private String extractJwtTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    private void authenticate(AuthRequest authRequest) throws Exception {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("Profile disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad Credentials");
        }
    }


    /**
     * Converting the profile request object to ProfileDTO
     *
     * @param profileRequest
     * @return ProfileDTO
     */
    private ProfileDTO mapToProfileDTO(@Valid ProfileRequest profileRequest) {
        return modelMapper.map(profileRequest, ProfileDTO.class);
    }

    /**
     * Converting the profile dto object to ProfileResponse
     *
     * @param profileDTO
     * @return ProfileResponse
     */
    private ProfileResponse mapToProfileResponse(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, ProfileResponse.class);
    }


}
