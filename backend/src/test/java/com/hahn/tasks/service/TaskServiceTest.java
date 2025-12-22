//package com.hahn.tasks.service;
//
//import com.hahn.tasks.dto.CreateTaskRequest;
//import com.hahn.tasks.dto.TaskResponse;
//import com.hahn.tasks.dto.UserDto;
//import com.hahn.tasks.model.Project;
//import com.hahn.tasks.model.Task;
//import com.hahn.tasks.model.User;
//import com.hahn.tasks.repository.ProjectRepository;
//import com.hahn.tasks.repository.TaskRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class TaskServiceTest {
//
//    @Mock
//    private TaskRepository taskRepository;
//
//    @Mock
//    private ProjectRepository projectRepository;
//
//    @InjectMocks
//    private TaskService taskService;
//
//    private UserDto user;
//    private Project project;
//    private Task task;
//
//    @BeforeEach
//    void setUp() {
//        user = new UserDto();
//        user.setId(1L);
//
//        project = new Project();
//        project.setId(10L);
//
//        task = new Task();
//        task.setId(100L);
//        task.setTitle("Test Task");
//        task.setDescription("Test Description");
//        task.setProject(project);
//    }
//
//    @Test
//    void createTask_shouldCreateAndReturnTaskResponse() {
//        CreateTaskRequest request = new CreateTaskRequest();
//        request.setTitle("New Task");
//        request.setDescription("Description");
//        request.setDueDate(LocalDate.now());
//
//        when(projectRepository.findById(10L)).thenReturn(Optional.of(project));
//        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        TaskResponse response = taskService.createTask(10L, request, user);
//
//        assertThat(response.getTitle()).isEqualTo("New Task");
//        assertThat(response.getDescription()).isEqualTo("Description");
//        assertThat(response.getDueDate()).isEqualTo(request.getDueDate());
//
//        verify(projectRepository).findById(10L);
//        verify(taskRepository).save(any(Task.class));
//    }
//
//    @Test
//    void getTasks_shouldReturnListOfTaskResponses() {
//        when(projectRepository.findById(10L)).thenReturn(Optional.of(project));
//        when(taskRepository.findAllByProject(project)).thenReturn(List.of(task));
//
//        List<TaskResponse> responses = taskService.getTasks(10L, user);
//
//        assertThat(responses).hasSize(1);
//        assertThat(responses.get(0).getTitle()).isEqualTo("Test Task");
//
//        verify(taskRepository).findAllByProject(project);
//    }
//
//    @Test
//    void markTaskCompleted_shouldUpdateTaskStatus() {
//        when(taskRepository.findById(100L)).thenReturn(Optional.of(task));
//        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        TaskResponse response = taskService.markTaskCompleted(10L, 100L, user);
//
//        assertThat(response.getStatus()).isEqualTo(task.getStatus());
//
//        verify(taskRepository).findById(100L);
//        verify(taskRepository).save(task);
//    }
//
//    @Test
//    void deleteTask_shouldDeleteTask() {
//        when(taskRepository.findById(100L)).thenReturn(Optional.of(task));
//
//        taskService.deleteTask(10L, 100L, user);
//
//        verify(taskRepository).delete(task);
//    }
//}
