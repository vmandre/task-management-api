package nz.co.solnet.domain.service;

import nz.co.solnet.domain.exception.TaskNotFoundException;
import nz.co.solnet.domain.model.Task;
import nz.co.solnet.domain.repository.TaskRepository;
import nz.co.solnet.domain.service.dto.TaskDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link TaskService}
 */
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskDTOMapper dtoMapper;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(taskRepository, dtoMapper);
    }


    @Test
    void givenTask_whenSave_thenReturnNewTask() {
        // given
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("A dummy task");

        Task task = new Task();

        // when
        when(taskRepository.save(any())).thenReturn(task);
        when(dtoMapper.apply(any())).thenReturn(taskDTO);
        TaskDTO newTask = taskService.save(taskDTO);

        // then
        verify(taskRepository, times(1)).save(any());
        assertNotNull(newTask);
    }

    @Test
    void whenGetAllTasks_thenReturnTasks() {
        // given
        List<Task> tasks = Arrays.asList(new Task(), new Task());

        // when
        when(taskRepository.findAll()).thenReturn(tasks);
        List<TaskDTO> allTasks = taskService.getAllTasks();

        // then
        assertEquals(2, allTasks.size());
    }

    @Test
    void whenGetAllOverdueTasks_thenReturnOverdueTasks() {
        // given
        List<Task> tasks = Arrays.asList(new Task(), new Task());

        // when
        when(taskRepository.findByDueDateBefore(any())).thenReturn(tasks);
        List<TaskDTO> allOverdueTasks = taskService.getAllOverdueTasks();

        // then
        assertEquals(2, allOverdueTasks.size());
    }

    @Test
    void givenInvalidId_whenGetTask_thenThrowException() {
        // given
        Integer taskId = 1;

        // when
        when(taskRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(taskId));
    }

    @Test
    void givenValidId_whenGetTask_thenReturnTask() {
        // given
        Integer taskId = 1;

        // when
        when(taskRepository.findById(any())).thenReturn(Optional.of(new Task()));
        when(dtoMapper.apply(any())).thenReturn(new TaskDTO());
        TaskDTO taskDTO = taskService.getTask(taskId);

        // then
        assertNotNull(taskDTO);
    }

    @Test
    void givenInvalidId_whenUpdate_thenThrowException() {
        // given
        TaskDTO invalidTask = new TaskDTO();

        // when
        when(taskRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThrows(TaskNotFoundException.class, () -> taskService.update(invalidTask));
    }
    @Test
    void givenTask_whenUpdate_thenReturnUpdatedTask() {
        // given
        TaskDTO taskDTO = new TaskDTO();

        // when
        when(taskRepository.findById(any())).thenReturn(Optional.of(new Task()));
        when(dtoMapper.apply(any())).thenReturn(new TaskDTO());
        TaskDTO updatedTask = taskService.update(taskDTO);

        // then
        assertNotNull(updatedTask);
    }

    @Test
    void givenInvalidId_whenDelete_thenThrowException() {
        // given
        Integer invalidId = 1;

        // when
        when(taskRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThrows(TaskNotFoundException.class, () -> taskService.delete(invalidId));
    }

    @Test
    void givenValidId_whenDelete_thenDeleteTask() {
        // given
        Integer id = 1;
        Task taskToDelete = new Task();

        // when
        when(taskRepository.findById(any())).thenReturn(Optional.of(taskToDelete));
        taskService.delete(id);

        // then
        verify(taskRepository, times(1)).delete(any());

    }
}