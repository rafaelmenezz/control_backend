package com.tcscontrol.control_backend.department.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DepartmentDTO (
    Long id, 
    @NotBlank @NotNull String nmDepartamento, 
    @NotNull Long id_usuario) {
}
