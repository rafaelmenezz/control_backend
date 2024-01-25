package com.tcscontrol.control_backend.requests.model.dto;

import java.util.List;

import com.tcscontrol.control_backend.constructions.model.dto.ConstructionDTO;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;

public record RequestsDTO(
      Long id,
      String dtPrevisaoRetirada,
      String dtRetirada,
      String dtPrevisaoDevolucao,
      String dtDevolucao,
      List<PatrimonyDTO> patrimonios,
      ConstructionDTO obra
) {}
