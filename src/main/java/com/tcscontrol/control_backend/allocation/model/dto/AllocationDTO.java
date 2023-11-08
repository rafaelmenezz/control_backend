package com.tcscontrol.control_backend.allocation.model.dto;

import java.util.List;

import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;

public record AllocationDTO(
        Long id,
        String dtAlocacao,
        String observation,
        List<PatrimonyDTO> patrimonies,
        DepartmentDTO departament) {
}
