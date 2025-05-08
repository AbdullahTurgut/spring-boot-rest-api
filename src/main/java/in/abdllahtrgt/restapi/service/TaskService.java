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
        TaskEntity task = taskRepository.findByTaskId(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found for the id" + taskId));
        log.info("Printing the task entity details {}", task);
        // map to task dto
        return mapToTaskDTO(task);
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
