package com.hahn.tasks.repository;

import com.hahn.tasks.model.Project;
import com.hahn.tasks.model.Task;
import com.hahn.tasks.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

        Page<Task> findByProject(Project project, Pageable pageable);


}
