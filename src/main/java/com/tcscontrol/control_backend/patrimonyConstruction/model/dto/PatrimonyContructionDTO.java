package com.tcscontrol.control_backend.patrimonyConstruction.model.dto;

import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;

public record PatrimonyContructionDTO(
      Long id,
      PatrimonyContructionDTO parent,
      String dtAlocacao,
      String dtDevolucao,
      String nmObservacao,
      PatrimonyDTO patrimonio,
      DepartmentDTO departamento
) {}
