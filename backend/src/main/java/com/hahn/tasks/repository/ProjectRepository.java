package com.hahn.tasks.repository;

import com.hahn.tasks.model.Project;
import com.hahn.tasks.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByUser(User user);

    Optional<Project> findByIdAndUser(Long id, User user);
}
