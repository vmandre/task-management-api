package nz.co.solnet.domain.service;

import jakarta.transaction.Transactional;
import nz.co.solnet.domain.exception.TaskNotFoundException;
import nz.co.solnet.domain.model.Task;
import nz.co.solnet.domain.repository.TaskRepository;
import nz.co.solnet.domain.service.dto.TaskDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    public static final String TASK_WITH_ID_NOT_FOUND = "task with id [%s] not found";
    private final TaskRepository repository;
    private final TaskDTOMapper taskDTOMapper;

    public TaskService(TaskRepository repository, TaskDTOMapper taskDTOMapper) {
        this.repository = repository;
        this.taskDTOMapper = taskDTOMapper;
    }

    @Transactional
    public TaskDTO save(TaskDTO taskDTO) {
        final Task task = new Task(taskDTO);
        repository.save(task);

        return taskDTOMapper.apply(task);

    }

    public List<TaskDTO> getAllTasks() {
        return repository.findAll()
                .stream()
                .map(taskDTOMapper)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getAllOverdueTasks() {
        return repository.findByDueDateBefore(LocalDate.now())
                .stream().map(taskDTOMapper)
                .collect(Collectors.toList());
    }

    public TaskDTO getTask(Integer id) {
        return repository.findById(id)
                .map(taskDTOMapper)
                .orElseThrow(() -> new TaskNotFoundException(
                        String.format(TASK_WITH_ID_NOT_FOUND, id)
                ));
    }

    @Transactional
    public TaskDTO update(TaskDTO taskDTO) {
        final Task task = repository.findById(taskDTO.getId())
                .orElseThrow(() -> new TaskNotFoundException(
                        String.format(TASK_WITH_ID_NOT_FOUND, taskDTO.getId())
                ));

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());

        repository.save(task);

        return taskDTOMapper.apply(task);

    }

    @Transactional
    public void delete(Integer id) {
        final Task task = findTask(id);
        repository.delete(task);
    }

    private Task findTask(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(
                        String.format(TASK_WITH_ID_NOT_FOUND, id)
                ));
    }
}
