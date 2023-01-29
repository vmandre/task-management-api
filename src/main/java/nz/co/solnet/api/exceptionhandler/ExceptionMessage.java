package nz.co.solnet.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ExceptionMessage {

    private Integer status;
    private OffsetDateTime timestamp;
    private String error;
    private List<Field> fields;

    @AllArgsConstructor
    @Getter
    public static class Field {
        private String name;
        private String message;
    }
}