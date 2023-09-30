package com.tcscontrol.control_backend.requests.model.dto;

import java.util.List;

import com.tcscontrol.control_backend.constructions.model.dto.ConstructionDTO;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;

public record RequestsDTO(
      Long id,
      String dtSolicitacao,
      String dtInicio,
      String dtDevolcao,
      List<PatrimonyDTO> patrimonios,
      ConstructionDTO obra
) {}
