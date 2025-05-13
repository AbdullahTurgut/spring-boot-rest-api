package in.abdllahtrgt.restapi.repository;

import in.abdllahtrgt.restapi.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
}
