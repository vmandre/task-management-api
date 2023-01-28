package nz.co.solnet.service;

import com.fasterxml.jackson.databind.cfg.MapperBuilder;
import nz.co.solnet.model.Task;
import nz.co.solnet.service.dto.TaskDTO;
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
