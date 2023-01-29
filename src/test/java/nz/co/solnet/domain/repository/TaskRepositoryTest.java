package nz.co.solnet.domain.repository;

import nz.co.solnet.domain.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link TaskRepository}
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository repository;

    @Test
    void givenPastDueDate_whenFindByDueDateBefore_thenReturnOverdueTask() {
        // given
        LocalDate now = LocalDate.now();

        Task task = new Task();
        task.setTitle("task 1");
        task.setDueDate(now.minusDays(1));
        task.setCreationDate(now);

        repository.save(task);

        // when
        List<Task> tasks = repository.findByDueDateBefore(now);

        // then
        assertEquals(1, tasks.size());
    }

    @Test
    void givenFutureDueDate_whenFindByDueDateBefore_thenReturnEmptyList() {
        // given
        LocalDate now = LocalDate.now();

        Task task = new Task();
        task.setTitle("task 1");
        task.setDueDate(now.plusDays(1));
        task.setCreationDate(now);

        repository.saveAndFlush(task);

        // when
        List<Task> tasks = repository.findByDueDateBefore(now);

        // then
        assertTrue(tasks.isEmpty());
    }

    @Test
    void givenInvalidTask_whenSave_thenThrowException() {
        // given
        Task invalidTask = new Task();

        // then
        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(invalidTask);
        });
    }
}