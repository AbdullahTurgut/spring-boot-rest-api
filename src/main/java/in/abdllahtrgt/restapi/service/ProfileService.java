package in.abdllahtrgt.restapi.service;

import in.abdllahtrgt.restapi.dto.ProfileDTO;
import in.abdllahtrgt.restapi.entity.ProfileEntity;
import in.abdllahtrgt.restapi.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileService implements IProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    /**
     * It will fetch the profile to db
     *
     * @param profileDTO
     * @return ProfileDTO
     */
    @Override
    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        ProfileEntity profileEntity = mapToProfileEntity(profileDTO);
        profileEntity.setProfileId(UUID.randomUUID().toString());
        // Call the repository method
        profileEntity = profileRepository.save(profileEntity);
        log.info("Printing the profile entity details {}", profileEntity);
        return mapToProfileDTO(profileEntity);
    }

    /**
     * Converting the profile entity object to ProfileDTO
     *
     * @param profileEntity
     * @return ProfileDTO
     */
    private ProfileDTO mapToProfileDTO(ProfileEntity profileEntity) {
        return modelMapper.map(profileEntity, ProfileDTO.class);
    }

    /**
     * Converting the profile dto object to ProfileEntity
     *
     * @param profileDTO
     * @return ProfileEntity
     */
    private ProfileEntity mapToProfileEntity(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, ProfileEntity.class);
    }
}
