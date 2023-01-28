package nz.co.solnet;

import nz.co.solnet.service.TaskService;
import nz.co.solnet.service.dto.TaskDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("task/api/v1/")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

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
    public TaskDTO addTask(@RequestBody TaskDTO taskDTO) {
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable Integer id) {
        service.delete(id);
    }
}
