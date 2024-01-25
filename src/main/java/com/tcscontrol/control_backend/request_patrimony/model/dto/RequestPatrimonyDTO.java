package com.tcscontrol.control_backend.request_patrimony.model.dto;

import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;

public record RequestPatrimonyDTO(
    Long id,
    String dtPrevisaoRetirada,
    String dtPrevisaoDevolucao,
    String dtRetirada,
    String dtDevolucao,
    PatrimonyDTO patrimonios
) {}
