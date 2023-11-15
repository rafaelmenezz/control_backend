package com.tcscontrol.control_backend.pessoa.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RecoverPassword(
    @NotBlank @NotNull String email
) {}
