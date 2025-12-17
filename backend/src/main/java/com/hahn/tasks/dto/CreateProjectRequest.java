package com.hahn.tasks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectRequest {

    @NotBlank
    private String title;

    private String description;
}
