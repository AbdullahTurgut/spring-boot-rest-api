package in.abdllahtrgt.restapi.handleException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorObject {

    private Integer statusCode;
    private String message;
    private Date timestamp;
    private String errorCode;
}
