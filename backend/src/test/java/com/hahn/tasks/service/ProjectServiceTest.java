//package com.hahn.tasks.service;
//
//import com.hahn.tasks.dto.CreateProjectRequest;
//import com.hahn.tasks.dto.ProjectResponse;
//import com.hahn.tasks.model.Project;
//import com.hahn.tasks.model.User;
//import com.hahn.tasks.repository.ProjectRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//class ProjectServiceTest {
//
//    @Mock
//    private ProjectRepository projectRepository;
//
//    @InjectMocks
//    private ProjectService projectService;
//
//    private User user;
//
//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//
//        user = new User();
//        user.setId(1L);
//        user.setEmail("user@test.com");
//    }
//
//    @Test
//    void shouldCreateProject() {
//        CreateProjectRequest request = new CreateProjectRequest();
//        request.setTitle("Test Project");
//        request.setDescription("Description");
//
//        Project savedProject = new Project();
//        savedProject.setId(10L);
//        savedProject.setTitle("Test Project");
//        savedProject.setDescription("Description");
//        savedProject.setUser(user);
//
//        when(projectRepository.save(any(Project.class)))
//                .thenReturn(savedProject);
//
//        ProjectResponse response = projectService.createProject(request);
//
//        assertThat(response.getId()).isEqualTo(10L);
//        assertThat(response.getTitle()).isEqualTo("Test Project");
//        assertThat(response.getTotalTasks()).isEqualTo(0);
//        assertThat(response.getProgressPercentage()).isEqualTo(0);
//
//        verify(projectRepository).save(any(Project.class));
//    }
//    @Test
//    void shouldReturnUserProjects() {
//        Project project = new Project();
//        project.setId(1L);
//        project.setTitle("My Project");
//        project.setUser(user);
//
//        when(projectRepository.findAllByUser(user))
//                .thenReturn(List.of(project));
//
//        List<ProjectResponse> projects = projectService.getProjects(user);
//
//        assertThat(projects).hasSize(1);
//        assertThat(projects.get(0).getTitle()).isEqualTo("My Project");
//    }
//
//    @Test
//    void shouldReturnProjectByIdForUser() {
//        Project project = new Project();
//        project.setId(5L);
//        project.setTitle("Project X");
//        project.setUser(user);
//
//        when(projectRepository.findByIdAndUser(5L, user))
//                .thenReturn(Optional.of(project));
//
//        ProjectResponse response = projectService.getProject(5L, user);
//
//        assertThat(response.getId()).isEqualTo(5L);
//        assertThat(response.getTitle()).isEqualTo("Project X");
//    }
//
//    @Test
//    void shouldThrowWhenProjectNotFound() {
//        when(projectRepository.findByIdAndUser(99L, user))
//                .thenReturn(Optional.empty());
//        try {
//            projectService.getProject(99L, user);
//        } catch (RuntimeException ex) {
//            assertThat(ex.getMessage()).isEqualTo("Project not found");
//        }
//    }
//}
