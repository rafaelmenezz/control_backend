package com.tcscontrol.control_backend.department.model.dto;

import com.tcscontrol.control_backend.pessoa.user.model.dto.UserCreateDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DepartmentDTO(
    Long id, 
    @NotBlank @NotNull String nmDepartamento, 
    @NotNull UserCreateDTO user)
{}
