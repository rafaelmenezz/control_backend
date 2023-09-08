package com.tcscontrol.control_backend.department.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DepartmentCreateDTO(
    Long id, 
    @NotBlank @NotNull String nmDepartamento, 
    @NotNull Long idUsuario
) {}
