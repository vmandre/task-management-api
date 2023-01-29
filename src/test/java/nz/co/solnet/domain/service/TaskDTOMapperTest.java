package nz.co.solnet.domain.service;

import nz.co.solnet.domain.model.Task;
import nz.co.solnet.domain.service.dto.TaskDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskDTOMapperTest {

    private TaskDTOMapper dtoMapper;

    @BeforeEach
    void setUp() {
        dtoMapper = new TaskDTOMapper();
    }

    @Test
    void givenTask_whenApply_thenReturnTaskDTO() {
        // given
        LocalDate now = LocalDate.now();
        Task task = new Task();
        task.setId(1);
        task.setTitle("dummy task");
        task.setDescription("This is a dummy task");
        task.setDueDate(now.plusDays(1));

        // when
        TaskDTO taskDTO = dtoMapper.apply(task);

        // then
        assertEquals(task.getId(), taskDTO.getId());
        assertEquals(task.getTitle(), taskDTO.getTitle());
        assertEquals(task.getDescription(), taskDTO.getDescription());
        assertEquals(task.getDueDate(), taskDTO.getDueDate());
    }
}