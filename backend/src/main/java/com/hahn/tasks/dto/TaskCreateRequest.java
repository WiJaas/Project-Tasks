package com.hahn.tasks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskCreateRequest {

    @NotBlank
    private String title;

    private String description;

    private LocalDate dueDate;
}
