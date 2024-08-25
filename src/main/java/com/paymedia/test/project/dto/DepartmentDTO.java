package com.paymedia.test.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DepartmentDTO {
    private Long id;

    @NotBlank(message = "Department name should be not empty")
    @NotNull(message = "Department name should be not null")
    private String name;
}
