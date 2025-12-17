package com.hahn.tasks.service;

import com.hahn.tasks.dto.CreateTaskRequest;
import com.hahn.tasks.dto.TaskResponse;
import com.hahn.tasks.model.Project;
import com.hahn.tasks.model.Task;
import com.hahn.tasks.model.User;
import com.hahn.tasks.repository.ProjectRepository;
import com.hahn.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;


    public TaskResponse createTask(Long projectId, CreateTaskRequest request, User user) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setProject(project);

        Task savedTask = taskRepository.save(task);

        return mapToResponse(savedTask);
    }

    public List<TaskResponse> getTasks(Long projectId, User user){

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return taskRepository.findAllByProject(project)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    public TaskResponse markTaskCompleted(Long projectId, Long taskId, User user) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.markCompleted();
        return mapToResponse(taskRepository.save(task));
    }
    public void deleteTask(Long projectId, Long taskId, User user) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskRepository.delete(task);    }

    private TaskResponse mapToResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setTitle(task.getTitle());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setDueDate(task.getDueDate());
        response.setStatus(task.getStatus());

        return response;
    }
}
