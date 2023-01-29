package nz.co.solnet.domain.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TaskDTO {

    private Integer id;
    @NotBlank
    private String title;
    private String description;
    private LocalDate dueDate;

}
