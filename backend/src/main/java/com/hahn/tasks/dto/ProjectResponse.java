package com.hahn.tasks.dto;

import lombok.*;

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
}
