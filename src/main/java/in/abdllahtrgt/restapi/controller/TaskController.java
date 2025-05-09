package in.abdllahtrgt.restapi.controller;

import in.abdllahtrgt.restapi.dto.TaskDTO;
import in.abdllahtrgt.restapi.response.TaskResponse;
import in.abdllahtrgt.restapi.service.ITaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
     * It will fetch the task by taskId from db
     *
     * @param taskId
     * @return TaskResponse
     */
    @GetMapping("/tasks/{taskId}")
    public TaskResponse getTaskById(@PathVariable String taskId) {
        log.info("Printing the task id {}", taskId);
        TaskDTO taskDTO = taskService.getTaskByTaskId(taskId);
        log.info("Printing the task details {}", taskDTO);
        return mapToResponse(taskDTO);
    }


    /**
     * It will delete the task by taskId from db
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/tasks/{taskId}/delete")
    public void deleteTaskByTaskId(@PathVariable String taskId) {
        log.info("API DELETE /tasks/{}/delete called", taskId);
        taskService.deleteTaskByTaskId(taskId);
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
