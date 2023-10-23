package com.tcscontrol.control_backend.requests.impl.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.constructions.impl.mapper.ConstructionMapper;
import com.tcscontrol.control_backend.request_patrimony.impl.mapper.RequestPatrimonyMapper;
import com.tcscontrol.control_backend.request_patrimony.model.dto.RequestPatrimonyDTO;
import com.tcscontrol.control_backend.requests.model.dto.RequestResponse;
import com.tcscontrol.control_backend.requests.model.entity.Requests;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RequestMapper {
      
      private ConstructionMapper constructionMapper;
      private RequestPatrimonyMapper requestPatrimonyMapper;


      public RequestResponse toResponse(Requests requests){
            if (UtilObjeto.isEmpty(requests)) {
                  return null;
            }

            List<RequestPatrimonyDTO> pDtos = requests.getPatrimonies()
            .stream()
            .map(requestPatrimonyMapper::toDTO)
            .collect(Collectors.toList());

            return new RequestResponse(
                  requests.getId(), 
                  constructionMapper.toDto(requests.getConstruction()), 
                  pDtos);
      }

      
}
