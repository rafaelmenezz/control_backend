package com.tcscontrol.control_backend.warranty.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.warranty.WarrantyNegocio;
import com.tcscontrol.control_backend.warranty.impl.mapper.WarrantyMapper;
import com.tcscontrol.control_backend.warranty.model.WarrantyRepository;
import com.tcscontrol.control_backend.warranty.model.dto.WarrantyDTO;

import lombok.AllArgsConstructor;

@Component(value = "warrantyNegocio" )
@AllArgsConstructor
public class WarrantyNegocioImpl implements WarrantyNegocio{
      
      private WarrantyRepository warrantyRepository;
      private WarrantyMapper warrantyMapper;
      private PatrimonyMapper patrimonyMapper;
      
      @Override
      public List<WarrantyDTO> list() {
            
            return warrantyRepository.findAll()
            .stream()
            .map(warrantyMapper::toDto)
            .collect(Collectors.toList());
      }

      @Override
      public WarrantyDTO findById(Long id) {
            return warrantyRepository.findById(id)
            .map(warrantyMapper::toDto)
            .orElseThrow(()-> new RecordNotFoundException(id));
      }

      @Override
      public WarrantyDTO create(WarrantyDTO warrantyDTO) {
            return warrantyMapper.toDto(warrantyRepository.save(warrantyMapper.toEntity(warrantyDTO)));

      }

      @Override
      public WarrantyDTO update(Long id, WarrantyDTO warrantyDTO) {
            return warrantyRepository.findById(id)
            .map(recordFound -> {
                  Patrimony patrimony = patrimonyMapper.toEntity(warrantyDTO.patrimonio());
                  recordFound.setDsGarantia(warrantyDTO.dsGarantia());
                  recordFound.setDtValidade(UtilData.toDate(warrantyDTO.dtValidade(), UtilData.FORMATO_DDMMAA));
                  recordFound.setPatrimony(patrimony);
                  return warrantyMapper.toDto(warrantyRepository.save(recordFound));
            }).orElseThrow(()-> new RecordNotFoundException(id));
      }
      
}
