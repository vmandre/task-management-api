package nz.co.solnet.domain.service;

import nz.co.solnet.domain.model.Task;
import nz.co.solnet.domain.service.dto.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TaskDTOMapper implements Function<Task, TaskDTO> {
    @Override
    public TaskDTO apply(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate()
        );
    }
}
