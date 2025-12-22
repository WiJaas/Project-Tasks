package com.hahn.tasks.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ProjectResponse {

    private Long id;
    private String title;
    private String description;

    private int totalTasks;
    private int completedTasks;
    private int progressPercentage;

    private List<TaskResponse> tasks;

}
