package in.abdllahtrgt.restapi.controller;

import in.abdllahtrgt.restapi.dto.TaskDTO;
import in.abdllahtrgt.restapi.request.TaskRequest;
import in.abdllahtrgt.restapi.response.TaskResponse;
import in.abdllahtrgt.restapi.service.ITaskService;
import jakarta.validation.Valid;
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
     * It will save the task details to db
     *
     * @param taskRequest
     * @return TaskResponse
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/tasks/save")
    public TaskResponse saveTaskDetails(@Valid @RequestBody TaskRequest taskRequest) {
        log.info("API POST /tasks/save called {}", taskRequest);
        TaskDTO taskDTO = mapToTaskDTO(taskRequest);
        taskDTO = taskService.saveTaskDetails(taskDTO);
        log.info("Printing the task dto {}", taskDTO);
        return mapToResponse(taskDTO);
    }

    /**
     * It will update the task details to db
     *
     * @param updateRequest
     * @param taskId
     * @return TaskResponse
     */
    @PutMapping("/tasks/{taskId}/update")
    public TaskResponse updateTaskDetails(@RequestBody TaskRequest updateRequest, @PathVariable String taskId) {
        log.info("API PUT /tasks/{}/update request body {}", taskId, updateRequest);
        TaskDTO updatedTaskDto = mapToTaskDTO(updateRequest);
        // TODO : call the service method to update the details
        updatedTaskDto = taskService.updateTaskDetails(updatedTaskDto, taskId);
        log.info("Printing the updated task dto details {}", updatedTaskDto);
        return mapToResponse(updatedTaskDto);
    }


    /**
     * Mapper method to convert task request to task dto
     *
     * @param taskRequest
     * @return TaskDTO
     */
    private TaskDTO mapToTaskDTO(@Valid TaskRequest taskRequest) {
        return modelMapper.map(taskRequest, TaskDTO.class);
    }

    /**
     * It will delete the task by taskId from db
     *
     * @param taskId
     * @return void
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
