package in.abdllahtrgt.restapi.repository;

import in.abdllahtrgt.restapi.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    // custom method
    Optional<ProfileEntity> findByEmail(String email);

    Boolean existsByEmail(String email);
}
