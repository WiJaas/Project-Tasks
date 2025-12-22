package com.hahn.tasks.controller;

import com.hahn.tasks.dto.CreateProjectRequest;
import com.hahn.tasks.dto.ProjectResponse;
import com.hahn.tasks.dto.UserDto;
import com.hahn.tasks.model.User;
import com.hahn.tasks.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /* ===============================
       CREATE PROJECT
    ================================ */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse createProject(
            @Valid @RequestBody CreateProjectRequest request
    ) {
        return projectService.createProject(request);
    }

    /* ===============================
       GET ALL PROJECTS (CURRENT USER)
    ================================ */

    @GetMapping
    public List<ProjectResponse> getProjects() {
        return projectService.getProjects();
    }

    /* ===============================
       GET SINGLE PROJECT (OPTIONAL)
    ================================ */

    @GetMapping("/{id}")
    public ProjectResponse getProject(@PathVariable Long id) {
        return projectService.getProject(id);
    }

    /* ===============================
       UPDATE PROJECT
    ================================ */

    @PutMapping("/{id}")
    public ProjectResponse updateProject(
            @PathVariable Long id,
            @Valid @RequestBody CreateProjectRequest request
    ) {
        return projectService.updateProject(id, request);
    }

    /* ===============================
       DELETE PROJECT
    ================================ */

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}

