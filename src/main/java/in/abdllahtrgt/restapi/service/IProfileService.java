package in.abdllahtrgt.restapi.service;

import in.abdllahtrgt.restapi.dto.ProfileDTO;

public interface IProfileService {

    /**
     * It will fetch the profile to db
     *
     * @param profileDTO
     * @return ProfileDTO
     */
    ProfileDTO createProfile(ProfileDTO profileDTO);
}
