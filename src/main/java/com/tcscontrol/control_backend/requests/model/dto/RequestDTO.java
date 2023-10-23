package com.tcscontrol.control_backend.requests.model.dto;

import java.util.List;

import com.tcscontrol.control_backend.constructions.model.dto.ConstructionDTO;
import com.tcscontrol.control_backend.request_patrimony.model.dto.RequestPatrimonyDTO;

public record RequestDTO(
    Long id, 
    ConstructionDTO obra,
    List<RequestPatrimonyDTO> patrimonios
) {}
