package nz.co.solnet.domain.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private Integer id;
    @NotBlank
    private String title;
    private String description;
    private LocalDate dueDate;
    private String status;

}
