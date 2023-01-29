package nz.co.solnet.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import nz.co.solnet.domain.service.dto.TaskDTO;

import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity(name = "tasks")
public class Task {

    public Task(TaskDTO taskDTO) {
        this.title = taskDTO.getTitle();
        this.description = taskDTO.getDescription();
        this.dueDate = taskDTO.getDueDate();
        this.creationDate = LocalDate.now();
        this.status = calculateStatus();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 256, nullable = false)
    private String title;

    @Column(length = 1024)
    private String description;

    private LocalDate dueDate;

    @Column(length = 10)
    private String status;

    @Column(nullable = false)
    private LocalDate creationDate;

    private String calculateStatus() {
        if (Objects.isNull(this.dueDate)) {
            return "";
        }

        return this.dueDate.isAfter(this.creationDate)
                ? "overdue"
                : "on time";
    }

}
