package com.hahn.tasks.repository;

import com.hahn.tasks.model.Project;
import com.hahn.tasks.model.Task;
import com.hahn.tasks.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByProject(Project project);
    Optional<Task> findByIdAndProject(Long id, Project project);

    long countAllByProject(Project project);

    long countByProjectAndStatus(Project project, TaskStatus status);

}
