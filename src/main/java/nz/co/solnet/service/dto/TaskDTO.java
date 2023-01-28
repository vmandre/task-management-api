package nz.co.solnet.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TaskDTO {

    private Integer id;
    private String title;
    private String description;
    private LocalDate dueDate;

}
