package com.tcscontrol.control_backend.patrimonyconstruction.impl.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.constructions.impl.mapper.ConstructionMapper;
import com.tcscontrol.control_backend.constructions.model.entity.Construction;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.patrimonyconstruction.model.dto.RequestsDTO;
import com.tcscontrol.control_backend.patrimonyconstruction.model.entity.Requests;
import com.tcscontrol.control_backend.utilitarios.UtilData;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RequestMapper {
      
      private PatrimonyMapper patrimonyMapper;
      private ConstructionMapper constructionMapper;

      public RequestsDTO toDto(Requests requests){
            if (requests == null) {
                  return null;
            }
            return new RequestsDTO(
            requests.getId(),
            UtilData.toString(requests.getDtSolicitacao(), UtilData.FORMATO_DDMMAA),
            UtilData.toString(requests.getDtInicio(), UtilData.FORMATO_DDMMAA), 
            UtilData.toString(requests.getDtDevolcao(), UtilData.FORMATO_DDMMAA),
            requests.getPatrimonios().stream().map(patrimonyMapper::toDto).collect(Collectors.toList()),
            constructionMapper.toDto(requests.getConstruction()));
      }

      public Requests toEntity(RequestsDTO requestDto){
            if (requestDto == null) {
                  return null;
            }
            Requests requests = new Requests();
            if (requestDto.id() != null) {
                  requests.setId(requestDto.id());
            }

            List<Patrimony> patrimonys = requestDto.patrimonios().stream().map(patrimonyMapper::toEntity).collect(Collectors.toList());
            Construction construction = constructionMapper.toEntity(requestDto.obra());

            requests.setDtSolicitacao(UtilData.toDate(requestDto.dtSolicitacao(), UtilData.FORMATO_DDMMAA));
            requests.setDtInicio(UtilData.toDate(requestDto.dtInicio(), UtilData.FORMATO_DDMMAA));
            requests.setDtDevolcao(UtilData.toDate(requestDto.dtDevolcao(), UtilData.FORMATO_DDMMAA));
            requests.setPatrimonios(patrimonys);
            requests.setConstruction(construction);

            return requests;
      }
}
