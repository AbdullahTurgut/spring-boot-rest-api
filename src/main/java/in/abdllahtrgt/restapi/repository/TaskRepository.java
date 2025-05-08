package in.abdllahtrgt.restapi.repository;

import in.abdllahtrgt.restapi.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Jpa repository for task resource
 * @author Abdullah
 */
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    /**
     * It will find the single task from db
     *
     * @param taskId
     * @return Optional
     */
    Optional<TaskEntity> findByTaskId(String taskId);
}

@