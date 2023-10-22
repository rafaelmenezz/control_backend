package com.tcscontrol.control_backend.constructions.model.dto;

import com.tcscontrol.control_backend.pessoa.user.model.dto.UserResponse;

public record ConstructionDTO(
        Long id,
        String nmObra,
        String nrCnpjCpf,
        String nmCliente,
        String nrCep,
        String nmLogradouro,
        Integer nrNumero,
        String nmBairro,
        String nmComplemento,
        String nmCidade,
        String nmUf,
        String dsObservacao,
        String dtInicio,
        String dtPrevisaoConclusao,
        UserResponse usuario) {
}
