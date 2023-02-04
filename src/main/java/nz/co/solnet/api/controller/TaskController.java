package nz.co.solnet.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nz.co.solnet.domain.service.TaskService;
import nz.co.solnet.domain.service.dto.TaskDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    @GetMapping
    public List<TaskDTO> getTasks() {
        return service.getAllTasks();
    }

    @GetMapping(path = "/overdue")
    public List<TaskDTO> getOverdueTasks() {
        return service.getAllOverdueTasks();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TaskDTO addTask(@Valid @RequestBody TaskDTO taskDTO) {
        return service.save(taskDTO);
    }

    @GetMapping(path = "/{id}")
    public TaskDTO getTask(@PathVariable Integer id) {
        return service.getTask(id);
    }

    @PutMapping(path = "/{id}")
    public TaskDTO updateTask(@PathVariable Integer id, @RequestBody TaskDTO taskDTO) {
        taskDTO.setId(id);
        return service.update(taskDTO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable Integer id) {
        service.delete(id);
    }
}
