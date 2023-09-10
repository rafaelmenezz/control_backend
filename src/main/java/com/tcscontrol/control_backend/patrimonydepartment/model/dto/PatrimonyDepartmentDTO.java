package com.tcscontrol.control_backend.patrimonydepartment.model.dto;

import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;

public record PatrimonyDepartmentDTO(
      Long id,
      PatrimonyDepartmentDTO parent,
      String dtAlocacao,
      String dtDevolucao,
      String nmObservacao,
      PatrimonyDTO patrimonio,
      DepartmentDTO departamento
) {}
