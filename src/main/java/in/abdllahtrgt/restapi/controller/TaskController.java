package in.abdllahtrgt.restapi.controller;

import in.abdllahtrgt.restapi.dto.TaskDTO;
import in.abdllahtrgt.restapi.response.TaskResponse;
import in.abdllahtrgt.restapi.service.ITaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This is the controller class of Task module
 *
 * @author Abdullah
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class TaskController {
    private final ITaskService taskService;
    private final ModelMapper modelMapper;

    /**
     * It will fetch the all tasks from db
     *
     * @return list
     */
    @GetMapping("/tasks")
    public List<TaskResponse> getAllTasks() {
        // call the service method
        List<TaskDTO> taskDTOList = taskService.getAllTasks();
        // Convert taskDTO to taskResponse
        List<TaskResponse> taskResponseList = taskDTOList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        // return the list
        return taskResponseList;
    }

    /**
     * Mapper method converting taskDTO to task response
     *
     * @param taskDTO
     * @return TaskResponse
     */
    private TaskResponse mapToResponse(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, TaskResponse.class);
    }
}
