package com.hahn.tasks.service;

import com.hahn.tasks.dto.CreateProjectRequest;
import com.hahn.tasks.dto.ProjectResponse;
import com.hahn.tasks.dto.TaskResponse;
import com.hahn.tasks.dto.UserDto;
import com.hahn.tasks.model.Project;
import com.hahn.tasks.model.TaskStatus;
import com.hahn.tasks.model.User;
import com.hahn.tasks.repository.ProjectRepository;
import com.hahn.tasks.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    /* ===============================
       AUTHENTICATED USER RESOLUTION
    ================================ */

    private UserDto getCurrentUserDto() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDto)) {
            throw new IllegalStateException("Unauthenticated user");
        }

        return (UserDto) authentication.getPrincipal();
    }

    private User getCurrentUser() {
        UserDto userDto = getCurrentUserDto();

        return userRepository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /* ===============================
       BUSINESS LOGIC
    ================================ */

    public ProjectResponse createProject(CreateProjectRequest request) {
        User user = getCurrentUser();

        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setUser(user);

        return mapToResponse(projectRepository.save(project));
    }
    @Transactional(readOnly = true)
    public ProjectResponse getProject(Long id) {
        User user = getCurrentUser();

        Project project = projectRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        return mapToResponse(project);
    }


    @Transactional(readOnly = true)
    public List<ProjectResponse> getProjects() {
        User user = getCurrentUser();

        return projectRepository.findAllByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public ProjectResponse updateProject(Long id, CreateProjectRequest request) {
        User user = getCurrentUser();

        Project project = projectRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Project not found or access denied"));

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());

        return mapToResponse(project);
    }

    @Transactional
    public void deleteProject(Long id) {
        User user = getCurrentUser();

        Project project = projectRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Project not found or access denied"));

        projectRepository.delete(project);
    }


    private ProjectResponse mapToResponse(Project project) {

        int totalTasks = project.getTasks() == null ? 0 : project.getTasks().size();
        int completedTasks = project.getTasks() == null
                ? 0
                : (int) project.getTasks().stream()
                .filter(t -> t.getStatus() == TaskStatus.COMPLETED)
                .count();

        int progress = totalTasks == 0 ? 0 : (completedTasks * 100) / totalTasks;

        ProjectResponse response = new ProjectResponse();
        response.setId(project.getId());
        response.setTitle(project.getTitle());
        response.setDescription(project.getDescription());
        response.setTotalTasks(totalTasks);
        response.setCompletedTasks(completedTasks);
        response.setProgressPercentage(progress);

        // Map tasks to DTOs
        response.setTasks(
                project.getTasks() == null ? List.of() :
                        project.getTasks().stream()
                                .map(task -> {
                                    TaskResponse t = new TaskResponse();
                                    t.setId(task.getId());
                                    t.setTitle(task.getTitle());
                                    t.setDescription(task.getDescription());
                                    t.setDueDate(task.getDueDate());
                                    t.setStatus(task.getStatus());
                                    return t;
                                })
                                .toList()
        );

        return response;


    }
}