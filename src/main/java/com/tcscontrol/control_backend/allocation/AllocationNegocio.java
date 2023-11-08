package com.tcscontrol.control_backend.allocation;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.model.entity.Allocation;

@Component
public interface AllocationNegocio extends AllocationService {

    String MSG_ALERTA_PATRIMONIO_INDISPONIVEL = "Patrimonios não disponivel para alocação!";

    Allocation save(Allocation allocation);

}
