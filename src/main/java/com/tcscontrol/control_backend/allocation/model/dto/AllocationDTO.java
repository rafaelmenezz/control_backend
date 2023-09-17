package com.tcscontrol.control_backend.allocation.model.dto;

import java.util.List;

import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;

public record AllocationDTO(
      Long id,
      AllocationDTO parent,
      String dtAlocacao,
      String dtDevolucao,
      String nmObservacao,
      List<PatrimonyDTO> patrimonios,
      DepartmentDTO departamento
) {}
