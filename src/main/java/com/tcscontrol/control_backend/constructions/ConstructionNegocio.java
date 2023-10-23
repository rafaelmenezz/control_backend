package com.tcscontrol.control_backend.constructions;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.constructions.model.dto.ConstructionDTO;
import com.tcscontrol.control_backend.constructions.model.entity.Construction;

@Component
public interface ConstructionNegocio extends ConstructionService {
      
    Construction toEntity(ConstructionDTO constructionDTO);
    
    ConstructionDTO toDTO(Construction construction);
}
