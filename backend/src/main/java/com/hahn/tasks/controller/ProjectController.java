package com.hahn.tasks.controller;

import com.hahn.tasks.dto.CreateProjectRequest;
import com.hahn.tasks.dto.ProjectResponse;
import com.hahn.tasks.model.User;
import com.hahn.tasks.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/projects")
@RestController
@RequiredArgsConstructor
public class ProjectController{

    private final ProjectService projectService;

    @PostMapping
    public ProjectResponse createProject(@Valid @RequestBody CreateProjectRequest request, @AuthenticationPrincipal User user) {
        return projectService.createProject(request, user);
    }

    @GetMapping
    public List<ProjectResponse> getProjects(@AuthenticationPrincipal User user) {
        return projectService.getProjects(user);
    }

    @GetMapping("/{id}")
    public ProjectResponse getProject(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return projectService.getProject(id, user);
    }

}
