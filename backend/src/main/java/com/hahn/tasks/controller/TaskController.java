package com.hahn.tasks.controller;

import com.hahn.tasks.dto.CreateTaskRequest;
import com.hahn.tasks.dto.TaskResponse;
import com.hahn.tasks.model.User;
import com.hahn.tasks.service.TaskService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequestMapping("/projects/{projectId}/tasks")
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskResponse createTask(
            @PathVariable Long projectId,
            @Valid @RequestBody CreateTaskRequest request,
            @AuthenticationPrincipal User user
    ){
        return taskService.createTask(projectId,request,user);

    }

    @GetMapping
    public List<TaskResponse> getTasks(@PathVariable Long projectId, @AuthenticationPrincipal User user){
        return taskService.getTasks(projectId, user);
    }

    @PutMapping("/{taskId}/complete")
    public TaskResponse completeTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @AuthenticationPrincipal User user
    ) {
        return taskService.markTaskCompleted(projectId, taskId, user);
    }


    @DeleteMapping("/{taskId}")
    public void deleteTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @AuthenticationPrincipal User user
    ) {
        taskService.deleteTask(projectId, taskId, user);
    }









}
