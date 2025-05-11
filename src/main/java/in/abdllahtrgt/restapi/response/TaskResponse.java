package in.abdllahtrgt.restapi.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponse {

    private String taskId;
    @NotBlank(message = "Task name is required")
    @Size(min = 3, message = "Task name should be at least 3 characters")
    private String name;
    @NotBlank(message = "Task status is required")
    private String status;
    @NotNull(message = "Task date is required")
    private Date date;

    private Timestamp createdAt;
    private Timestamp updatedAt;
}
