package nz.co.solnet.service;

import jakarta.transaction.Transactional;
import nz.co.solnet.exception.ResourceNotFoundException;
import nz.co.solnet.model.Task;
import nz.co.solnet.repository.TaskRepository;
import nz.co.solnet.service.dto.TaskDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final TaskDTOMapper taskDTOMapper;

    public TaskService(TaskRepository repository, TaskDTOMapper taskDTOMapper) {
        this.repository = repository;
        this.taskDTOMapper = taskDTOMapper;
    }

    public TaskDTO save(TaskDTO taskDTO) {
        Task task = new Task(taskDTO);
        task = repository.save(task);

        return taskDTOMapper.apply(task);

    }

    public List<TaskDTO> getAllTasks() {
        return repository.findAll()
                .stream()
                .map(taskDTOMapper)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getAllOverdueTasks() {
        return repository.findByDueDateAfter(LocalDate.now())
                .stream().map(taskDTOMapper)
                .collect(Collectors.toList());
    }

    public TaskDTO getTask(Integer id) {
        return repository.findById(id)
                .map(taskDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("task with id [%s] not found", id)
                ));
    }

    @Transactional
    public TaskDTO update(TaskDTO taskDTO) {
        Task task = repository.findById(taskDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("task with id [%s] not found", taskDTO.getId())
                ));

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());

        repository.save(task);

        return taskDTOMapper.apply(task);

    }

    public void delete(Integer id) {
        boolean notExists = !repository.existsById(id);

        if (notExists) {
            throw new ResourceNotFoundException(
                    String.format("task with id [%s] not found", id)
            );
        }

        repository.deleteById(id);
    }

    private Task findTask(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("task with id [%s] not found", id)
                ));
    }
}
