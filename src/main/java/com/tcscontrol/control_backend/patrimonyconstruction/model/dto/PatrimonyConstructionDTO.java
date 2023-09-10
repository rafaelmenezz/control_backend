package com.tcscontrol.control_backend.patrimonyconstruction.model.dto;

import com.tcscontrol.control_backend.constructions.model.dto.ConstructionDTO;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;

public record PatrimonyConstructionDTO(
      Long id,
      PatrimonyDTO patrimonio,
      ConstructionDTO obra
) {}
