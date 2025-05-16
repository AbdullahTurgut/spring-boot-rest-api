package in.abdllahtrgt.restapi.service;

import in.abdllahtrgt.restapi.entity.ProfileEntity;

public interface IAuthService {
    ProfileEntity getLoggedInProfile();
}
