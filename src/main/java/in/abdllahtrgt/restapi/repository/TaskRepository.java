package in.abdllahtrgt.restapi.repository;

import in.abdllahtrgt.restapi.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
