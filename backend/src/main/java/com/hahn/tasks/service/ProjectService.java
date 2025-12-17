package com.hahn.tasks.service;

import com.hahn.tasks.dto.CreateProjectRequest;

import com.hahn.tasks.dto.ProjectResponse;
import com.hahn.tasks.model.Project;
import com.hahn.tasks.model.User;
import com.hahn.tasks.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;


    public ProjectResponse createProject(CreateProjectRequest request, User user) {
        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setUser(user);


        Project saved = projectRepository.save(project);

        ProjectResponse response = new ProjectResponse();
        response.setId(saved.getId()); // 🔴 THIS WAS MISSING
        response.setTitle(saved.getTitle());
        response.setDescription(saved.getDescription());
        response.setTotalTasks(0);
        response.setCompletedTasks(0);
        response.setProgressPercentage(0);
    return response;    }



    public List<ProjectResponse> getProjects(User user){
        return projectRepository.findAllByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProjectResponse getProject(Long id, User user){
        Project project = projectRepository.findByIdAndUser(id,user)
                .orElseThrow(()-> new RuntimeException("Project not found"));
        return mapToResponse(project);
    }

    private ProjectResponse mapToResponse(Project project) {
        int totalTasks = project.getTasks() == null ? 0 : project.getTasks().size();
        int completedTasks = 0;

        int progress = totalTasks == 0 ? 0 :(completedTasks * 100 )/ totalTasks;

        ProjectResponse response = new ProjectResponse();

        response.setId(project.getId());
        response.setTitle(project.getTitle());
        response.setDescription(project.getDescription());
        response.setTotalTasks(totalTasks);
        response.setCompletedTasks(completedTasks);
        response.setProgressPercentage(progress);
        return response;

    }

}
