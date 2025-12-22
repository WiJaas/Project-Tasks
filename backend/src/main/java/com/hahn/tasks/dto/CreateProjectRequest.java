package com.hahn.tasks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CreateProjectRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
}
