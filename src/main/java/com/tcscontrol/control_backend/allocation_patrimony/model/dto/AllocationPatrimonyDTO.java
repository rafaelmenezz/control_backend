package com.tcscontrol.control_backend.allocation_patrimony.model.dto;

import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;

public record AllocationPatrimonyDTO(
    Long id,
    String dtAlocacao,
    String dtDevolucao,
    String nmObservacao,
    PatrimonyDTO patrimonio
) {}

