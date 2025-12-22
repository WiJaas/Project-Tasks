package com.hahn.tasks.service;

import com.hahn.tasks.dto.CreateTaskRequest;
import com.hahn.tasks.dto.TaskResponse;
import com.hahn.tasks.model.Project;
import com.hahn.tasks.model.Task;
import com.hahn.tasks.model.User;
import com.hahn.tasks.dto.UserDto;
import com.hahn.tasks.repository.ProjectRepository;
import com.hahn.tasks.repository.TaskRepository;
import com.hahn.tasks.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = (UserDto) auth.getPrincipal();

        return userRepository.findById(userDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public TaskResponse createTask(Long projectId, CreateTaskRequest request) {
        User user = getCurrentUser();

        Project project = projectRepository.findByIdAndUser(projectId, user)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setProject(project);

        return mapToResponse(taskRepository.save(task));
    }
    @Transactional(readOnly = true)
    public Page<TaskResponse> getTasks(
            Long projectId,
            int page,
            int size
    ) {
        User user = getCurrentUser();

        Project project = projectRepository.findByIdAndUser(projectId, user)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return taskRepository.findByProject(project, pageable)
                .map(this::mapToResponse);
    }


    @Transactional
    public TaskResponse markTaskCompleted(Long projectId, Long taskId) {
        User user = getCurrentUser();

        Project project = projectRepository.findByIdAndUser(projectId, user)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if (!task.getProject().getId().equals(project.getId())) {
            throw new AccessDeniedException("Access denied");
        }

        task.markCompleted();
        return mapToResponse(task);
    }
    @Transactional
    public TaskResponse updateTask(
            Long projectId,
            Long taskId,
            CreateTaskRequest request
    ) {
        User user = getCurrentUser();

        Project project = projectRepository.findByIdAndUser(projectId, user)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if (!task.getProject().getId().equals(project.getId())) {
            throw new AccessDeniedException("Access denied");
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());

        return mapToResponse(task);
    }

    @Transactional
    public void deleteTask(Long projectId, Long taskId) {
        User user = getCurrentUser();

        Project project = projectRepository.findByIdAndUser(projectId, user)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if (!task.getProject().getId().equals(project.getId())) {
            throw new AccessDeniedException("Access denied");
        }

        taskRepository.delete(task);
    }


    private TaskResponse mapToResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId()); // ← crucial
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setDueDate(task.getDueDate());
        response.setStatus(task.getStatus());
        return response;
    }

}
