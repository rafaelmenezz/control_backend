package com.tcscontrol.control_backend.constructions.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConstructionsDTO(
    long id,
    @NotBlank @NotNull String nmObra,
    //String nmObservacao,
    @NotBlank @NotNull String nmCnpjCpf,
    @NotBlank @NotNull String nmCliente,
    @NotNull String nmCep,
    @NotNull String nmLogradouro,
    @NotNull String nmNumero,
    String nmComplemento,
    @NotNull String nmBairro,
    @NotNull String nmCidade,
    @NotNull String nmEstado,
    @NotNull long id_usuario_responsavel
    //Date dtInicio,
    //Date dtPrevConclusao,
    //Date dtFim
) {}