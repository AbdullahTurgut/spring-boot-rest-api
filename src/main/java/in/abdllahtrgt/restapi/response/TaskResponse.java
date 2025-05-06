package in.abdllahtrgt.restapi.response;

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
    private String name;
    private String status;
    private Date date;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
