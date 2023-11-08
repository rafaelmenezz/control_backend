package com.tcscontrol.control_backend.allocation.dto;

import java.util.List;

import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;

public record AllocationResponse(
                Long id,
                String dtAlocacao,
                String nmObservacao,
                DepartmentDTO department,
                List<PatrimonyDTO> patrimonies) {
}
