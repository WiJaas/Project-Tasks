package com.hahn.tasks.controller;

import com.hahn.tasks.dto.CreateTaskRequest;
import com.hahn.tasks.dto.TaskResponse;
import com.hahn.tasks.dto.UserDto;
import com.hahn.tasks.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

    private final TaskService taskService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(
            @PathVariable Long projectId,
            @Valid @RequestBody CreateTaskRequest request
    ) {
        return taskService.createTask(projectId, request);
    }



    @GetMapping
    public Page<TaskResponse> getTasks(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return taskService.getTasks(projectId, page, size);
    }

    @PatchMapping("/{taskId}/complete")
    public TaskResponse markCompleted(
            @PathVariable Long projectId,
            @PathVariable Long taskId
    ) {
        return taskService.markTaskCompleted(projectId, taskId);
    }
    @PutMapping("/{taskId}")
    public TaskResponse updateTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @Valid @RequestBody CreateTaskRequest request
    ) {
        return taskService.updateTask(projectId, taskId, request);
    }



    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId
    ) {
        taskService.deleteTask(projectId, taskId);
    }
}
