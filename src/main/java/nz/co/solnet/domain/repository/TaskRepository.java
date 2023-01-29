package nz.co.solnet.domain.repository;

import nz.co.solnet.domain.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByDueDateBefore(LocalDate dueDate);
}
