package com.paymedia.test.project.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmployeeDTO {
    private Long id;

    @NotBlank(message = "First name should not be blank")
    @NotNull(message = "First name should not be null")
    private String firstName;

    @NotBlank(message = "Last name should not be blank")
    @NotNull(message = "Last name should not be null")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email should not be blank")
    @NotNull(message = "Email should not be null")
    private String email;

    @NotBlank(message = "Phone number should not be blank")
    @NotNull(message = "Phone number should not be null")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;

    private Long departmentId;
}
