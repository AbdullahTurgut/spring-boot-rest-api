package in.abdllahtrgt.restapi.service;


import in.abdllahtrgt.restapi.dto.TaskDTO;

import java.util.List;

/**
 * Service interface for Task module
 *
 * @author Abdullah
 */
public interface ITaskService {
    /**
     * It will fetch the tasks from db
     *
     * @return list
     */
    List<TaskDTO> getAllTasks();

    /**
     * It will fetch the task by taskId from db
     *
     * @param taskId
     * @return TaskDTO
     */
    TaskDTO getTaskByTaskId(String taskId);
}
