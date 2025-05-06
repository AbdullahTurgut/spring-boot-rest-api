package in.abdllahtrgt.restapi.service;

import in.abdllahtrgt.restapi.dto.TaskDTO;
import in.abdllahtrgt.restapi.entity.TaskEntity;
import in.abdllahtrgt.restapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for Task module
 *
 * @author Abdullah
 */
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
     * Mapper method for converting task entity object to task dto
     *
     * @param taskEntity
     * @return TaskDTO
     */
    private TaskDTO mapToTaskDTO(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, TaskDTO.class);
    }
}
