package com.tcscontrol.control_backend.constructions;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcscontrol.control_backend.constructions.model.dto.ConstructionDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public interface ConstructionService {

      List<ConstructionDTO> list();

      ConstructionDTO findById(Long id);

      ConstructionDTO create(ConstructionDTO constructionDTO);

      ConstructionDTO update(Long id, ConstructionDTO constructionDTO);

      public void delete(@PathVariable @NotNull @Positive Long id);
      
}
