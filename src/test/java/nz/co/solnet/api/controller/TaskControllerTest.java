package nz.co.solnet.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.solnet.domain.exception.TaskNotFoundException;
import nz.co.solnet.domain.service.TaskService;
import nz.co.solnet.domain.service.dto.TaskDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    private static final String URL_TASKS = "/tasks";
    private static final String URL_OVERDUE_TASKS = "/tasks/overdue";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TaskService taskService;

    @Test
    void givenTasks_whenGetTasks_thenReturnJsonArray() throws Exception {
        // given
        TaskDTO taskDTO = new TaskDTO(
                1,
                "dummy task",
                "This is a dummy task",
                LocalDate.now()
        );
        List<TaskDTO> allTasks = Arrays.asList(taskDTO);

        // when
        when(taskService.getAllTasks()).thenReturn(allTasks);

        // then
        mvc.perform(get(URL_TASKS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("dummy task")));
    }
    @Test
    void givenNoTasks_whenGetTasks_thenReturnEmptyJsonArray() throws Exception {
        // given
        List<TaskDTO> allTasks = new ArrayList<>();

        // when
        when(taskService.getAllTasks()).thenReturn(allTasks);

        // then
        mvc.perform(get(URL_TASKS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void givenOverdueTasks_whenGetOverdueTasks_thenReturnJsonArray() throws Exception {
        // given
        TaskDTO taskDTO = new TaskDTO(
                1,
                "overdue task",
                "This is an overdue task",
                LocalDate.now().minusDays(10)
        );
        List<TaskDTO> allOverdueTasks = Arrays.asList(taskDTO);

        // when
        when(taskService.getAllOverdueTasks()).thenReturn(allOverdueTasks);

        // then
        mvc.perform(get(URL_OVERDUE_TASKS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("overdue task")));
    }

    @Test
    void givenTask_whenAddTask_thenReturnJson() throws Exception {
        // given
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("dummy task");

        // when
        when(taskService.save(any())).thenReturn(taskDTO);

        // then
        mvc.perform(post(URL_TASKS)
                .content(asJsonString(taskDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("dummy task")));
    }

    @Test
    void givenInvalidTask_whenAddTask_thenReturnBadRequest() throws Exception {
        // given
        TaskDTO taskDTO = new TaskDTO();

        // when
        when(taskService.save(taskDTO)).thenReturn(taskDTO);

        // then
        mvc.perform(post(URL_TASKS)
                .content(asJsonString(taskDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenId_whenGetTask_thenReturnTask() throws Exception {
        // given
        TaskDTO taskDTO = new TaskDTO(
                1,
                "dummy task",
                "This is a dummy task",
                LocalDate.now()
        );

        // when
        when(taskService.getTask(1)).thenReturn(taskDTO);

        // then
        mvc.perform(get(URL_TASKS + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("dummy task")));
    }

    @Test
    void givenInvalidId_whenGetTask_thenReturnNotFound() throws Exception {
        // given
        TaskDTO taskDTO = new TaskDTO(
                1,
                "dummy task",
                "This is a dummy task",
                LocalDate.now()
        );

        // when
        when(taskService.getTask(1)).thenThrow(TaskNotFoundException.class);

        // then
        mvc.perform(get(URL_TASKS + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenId_whenUpdateTask_thenReturnTask() throws Exception {
        // given
        Integer taskId = 1;
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("dummy task");

        // when
        when(taskService.update(any())).thenReturn(taskDTO);

        // then
        mvc.perform(put(URL_TASKS + "/" + taskId)
                .content(asJsonString(taskDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("dummy task")));
    }

    @Test
    void givenInvalidId_whenUpdateTask_thenReturnNotFound() throws Exception {
        // given
        Integer notFoundTaskId = 1;
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("dummy task");

        // when
        when(taskService.update(any())).thenThrow(TaskNotFoundException.class);

        // then
        mvc.perform(put(URL_TASKS + "/" + notFoundTaskId)
                .content(asJsonString(taskDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenId_whenDeleteTask_thenReturnNoContent() throws Exception {
        // given
        Integer taskId = 1;

        // then
        mvc.perform(delete(URL_TASKS + "/" + taskId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}