package in.abdllahtrgt.restapi.controller;

import in.abdllahtrgt.restapi.dto.ProfileDTO;
import in.abdllahtrgt.restapi.request.ProfileRequest;
import in.abdllahtrgt.restapi.response.ProfileResponse;
import in.abdllahtrgt.restapi.service.IProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
