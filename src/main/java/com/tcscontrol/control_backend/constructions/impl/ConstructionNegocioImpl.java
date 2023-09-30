package com.tcscontrol.control_backend.constructions.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.constructions.ConstructionNegocio;
import com.tcscontrol.control_backend.constructions.ConstructionRepository;
import com.tcscontrol.control_backend.constructions.impl.mapper.ConstructionMapper;
import com.tcscontrol.control_backend.constructions.model.dto.ConstructionDTO;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.pessoa.user.impl.mapper.UserMapper;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;
import com.tcscontrol.control_backend.utilitarios.UtilData;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@Component(value = "constructionNegocio")
@AllArgsConstructor
public class ConstructionNegocioImpl implements ConstructionNegocio {

      private ConstructionRepository constructionRepository;
      private ConstructionMapper constructionMapper;
      private UserMapper userMapper;

      @Override
      public List<ConstructionDTO> list() {
            return constructionRepository.findAll()
                        .stream()
                        .map(constructionMapper::toDto)
                        .collect(Collectors.toList());

      }

      @Override
      public ConstructionDTO findById(Long id) {
            return constructionRepository.findById(id)
                        .map(constructionMapper::toDto)
                        .orElseThrow(() -> new RecordNotFoundException(id));
      }

      @Override
      public ConstructionDTO create(ConstructionDTO constructionDTO) {
            return constructionMapper.toDto(constructionRepository.save(constructionMapper.toEntity(constructionDTO)));
      }

      @Override
      public ConstructionDTO update(Long id, ConstructionDTO constructionDTO) {
            return constructionRepository.findById(id)
                        .map(recordFound -> {
                              User user = userMapper.toCreateEntity(constructionDTO.usuario());
                              recordFound.setNmObra(constructionDTO.nmObra());
                              recordFound.setNrCnpjCpf(constructionDTO.nrCnpjCpf());
                              recordFound.setNmCliente(constructionDTO.nmCliente());
                              recordFound.setNrCep(constructionDTO.nrCep());
                              recordFound.setNmLogradouro(constructionDTO.nmLogradouro());
                              recordFound.setNrNumero(constructionDTO.nrNumero());
                              recordFound.setNmBairro(constructionDTO.nmBairro());
                              recordFound.setNmComplemento(constructionDTO.nmComplemento());
                              recordFound.setNmCidade(constructionDTO.nmCidade());
                              recordFound.setNmUf(constructionDTO.nmUf());
                              recordFound.setDsObservacao(constructionDTO.dsObservacao());
                              recordFound.setDtInicio(
                                          UtilData.toDate(constructionDTO.dtInicio(), UtilData.FORMATO_DDMMAA));
                              recordFound.setDtPrevisaoConclusao(UtilData.toDate(constructionDTO.dtPrevisaoConclusao(),
                                          UtilData.FORMATO_DDMMAA));
                              recordFound.setDtFim(UtilData.toDate(constructionDTO.dtFim(), UtilData.FORMATO_DDMMAA));
                              recordFound.setUser(user);
                              return constructionMapper.toDto(constructionRepository.save(recordFound));
                        }).orElseThrow(() -> new RecordNotFoundException(id));
      }

      @Override
      public void delete(@NotNull @Positive Long id) {
            constructionRepository
                        .delete(constructionRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
      }

}
