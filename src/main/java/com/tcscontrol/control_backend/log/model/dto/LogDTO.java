package com.tcscontrol.control_backend.log.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LogDTO(
    Long id,
    @NotNull Long id_patrimonio,
    @NotNull Long id_usuario,
    @NotBlank @NotNull String vl_valor_anterior,
    @NotBlank @NotNull String vl_valor_atual,
    @NotBlank @NotNull String nm_nome_campo_alterado,
    @NotBlank @NotNull String dt_data_realizada
) {}
