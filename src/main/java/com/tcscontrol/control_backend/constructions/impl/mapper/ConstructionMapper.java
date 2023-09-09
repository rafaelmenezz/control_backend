package com.tcscontrol.control_backend.constructions.impl.mapper;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.constructions.model.dto.ConstructionDTO;
import com.tcscontrol.control_backend.constructions.model.entity.Construction;
import com.tcscontrol.control_backend.pessoa.user.impl.mapper.UserMapper;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;
import com.tcscontrol.control_backend.utilitarios.UtilData;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ConstructionMapper {
      
      UserMapper userMapper;

      public ConstructionDTO toDto(Construction construction){
            if (construction == null) {
                  return null;
            }

            return new ConstructionDTO(
                  construction.getId(), 
                  construction.getNmObra(), 
                  construction.getNrCnpjCpf(), 
                  construction.getNmCliente(), 
                  construction.getNrCep(),
                  construction.getNmLogradouro(), 
                  construction.getNrNumero(), 
                  construction.getNmCidade(),
                  construction.getNmUf(), 
                  construction.getDsObservacao(),
                  UtilData.toString(construction.getDtInicio(), UtilData.FORMATO_DDMMAA), 
                  UtilData.toString(construction.getDtPrevisaoConclusao(), UtilData.FORMATO_DDMMAA), 
                  UtilData.toString(construction.getDtFim(), UtilData.FORMATO_DDMMAA), 
                  userMapper.toCreateDto(construction.getUser()));
      }

      public Construction toEntity(ConstructionDTO constructionDTO){
            
            if (constructionDTO == null) {
                  return null;
            }
            Construction construction = new Construction();
            if (constructionDTO.id() != null) {
                  construction.setId(constructionDTO.id());
            }

            User user = userMapper.toCreateEntity(constructionDTO.usuario());

            construction.setNmObra(constructionDTO.nmObra());
            construction.setNrCnpjCpf(constructionDTO.nrCnpjCpf());
            construction.setNmCliente(constructionDTO.nmCliente());
            construction.setNrCep(constructionDTO.nrCep());
            construction.setNmLogradouro(constructionDTO.nmLogradouro());
            construction.setNrNumero(constructionDTO.nrNumero());
            construction.setNmCidade(constructionDTO.nmCidade());
            construction.setNmUf(constructionDTO.nmUf());
            construction.setDsObservacao(constructionDTO.dsObservacao());
            construction.setDtInicio(UtilData.toDate(constructionDTO.dtInicio(), UtilData.FORMATO_DDMMAA));
            construction.setDtPrevisaoConclusao(UtilData.toDate(constructionDTO.dtPrevisaoConclusao(), UtilData.FORMATO_DDMMAA));
            construction.setDtFim(UtilData.toDate(constructionDTO.dtFim(), UtilData.FORMATO_DDMMAA));
            construction.setUser(user);


            return construction;
      }

}
