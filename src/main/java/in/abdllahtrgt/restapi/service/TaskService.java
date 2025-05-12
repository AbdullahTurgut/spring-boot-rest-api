package in.abdllahtrgt.restapi.service;

import in.abdllahtrgt.restapi.dto.TaskDTO;
import in.abdllahtrgt.restapi.entity.TaskEntity;
import in.abdllahtrgt.restapi.handleException.ResourceNotFoundException;
import in.abdllahtrgt.restapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service implementation for Task module
 *
 * @author Abdullah
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    /**
     * It will fetch the tasks from db
     *
     * @return list
     */
    @Override
    public List<TaskDTO> getAllTasks() {
        // call the repository method
        List<TaskEntity> taskList = taskRepository.findAll();
        //convert the entity object to dto
        List<TaskDTO> taskDTOList = taskList.stream()
                .map(this::mapToTaskDTO)
                .collect(Collectors.toList());
        // return the list
        return taskDTOList;
    }

    /**
     * It will fetch the task by taskId from db
     *
     * @param taskId
     * @return TaskDTO
     */
    @Override
    public TaskDTO getTaskByTaskId(String taskId) {
        // call repository
        TaskEntity task = getEntity(taskId);
        log.info("Printing the task entity {}", task);
        // map to task dto
        return mapToTaskDTO(task);
    }


    /**
     * It will delete the task by taskId from db
     *
     * @param taskId
     * @return void
     */
    @Override
    public void deleteTaskByTaskId(String taskId) {
        TaskEntity task = getEntity(taskId);
        log.info("Printing the task entity {}", task);
        taskRepository.delete(task);
    }


    /**
     * It will save the task details to db
     *
     * @param taskDTO
     * @return TaskDTO
     */
    @Override
    public TaskDTO saveTaskDetails(TaskDTO taskDTO) {
        TaskEntity taskEntity = mapToTaskEntity(taskDTO);
        taskEntity.setTaskId(UUID.randomUUID().toString());
        taskEntity = taskRepository.save(taskEntity);
        log.info("Printing the task entity details {}", taskEntity);
        return mapToTaskDTO(taskEntity);
    }

    @Override
    public TaskDTO updateTaskDetails(TaskDTO taskDTO, String taskId) {
        TaskEntity existingTask = getEntity(taskId);
        TaskEntity updatedTask = mapToTaskEntity(taskDTO);
        updatedTask.setId(existingTask.getId());
        updatedTask.setTaskId(existingTask.getTaskId());
        updatedTask.setCreatedAt(existingTask.getCreatedAt());
        updatedTask.setUpdatedAt(existingTask.getUpdatedAt());
        updatedTask = taskRepository.save(updatedTask);
        log.info("Printing the updated task entity details {}", updatedTask);
        return mapToTaskDTO(updatedTask);
    }

    /**
     * Mapper method to convert task dto to task entity
     *
     * @param taskDTO
     * @return TaskEntity
     */
    private TaskEntity mapToTaskEntity(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, TaskEntity.class);
    }


    /**
     * It will fetch the task by taskId from db
     *
     * @param taskId
     * @return TaskEntity
     */
    private TaskEntity getEntity(String taskId) {
        TaskEntity task = taskRepository.findByTaskId(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found for the id" + taskId));
        return task;
    }


    /**
     * Mapper method for converting task entity object to task dto
     *
     * @param taskEntity
     * @return TaskDTO
     */
    private TaskDTO mapToTaskDTO(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, TaskDTO.class);
    }
}
