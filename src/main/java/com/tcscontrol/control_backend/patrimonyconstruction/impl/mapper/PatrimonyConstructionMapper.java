package com.tcscontrol.control_backend.patrimonyconstruction.impl.mapper;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.constructions.impl.mapper.ConstructionMapper;
import com.tcscontrol.control_backend.constructions.model.entity.Construction;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.patrimonyconstruction.model.dto.PatrimonyConstructionDTO;
import com.tcscontrol.control_backend.patrimonyconstruction.model.entity.PatrimonyConstruction;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PatrimonyConstructionMapper {
      
      private PatrimonyMapper patrimonyMapper;
      private ConstructionMapper constructionMapper;


      public PatrimonyConstructionDTO toDto(PatrimonyConstruction patrimonyConstruction){
            if (patrimonyConstruction == null) {
                  return null;
            }
            return new PatrimonyConstructionDTO(
            patrimonyConstruction.getId(),
            patrimonyMapper.toDto(patrimonyConstruction.getPatrimonio()),
            constructionMapper.toDto(patrimonyConstruction.getConstruction()));

      }

      public PatrimonyConstruction toEntity(PatrimonyConstructionDTO pcdto){
            if (pcdto == null) {
                  return null;
            }
            PatrimonyConstruction patrimonyConstruction = new PatrimonyConstruction();
            if (pcdto.id() != null) {
                  patrimonyConstruction.setId(pcdto.id());
            }

            Patrimony patrimony = patrimonyMapper.toEntity(pcdto.patrimonio());
            Construction construction = constructionMapper.toEntity(pcdto.obra());

            patrimonyConstruction.setPatrimonio(patrimony);
            patrimonyConstruction.setConstruction(construction);

            return patrimonyConstruction;
      }
}
