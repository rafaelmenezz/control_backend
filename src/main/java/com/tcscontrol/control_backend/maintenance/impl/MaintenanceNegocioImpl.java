package com.tcscontrol.control_backend.maintenance.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.maintenance.MaintenanceNegocio;
import com.tcscontrol.control_backend.maintenance.MaintenanceRepository;
import com.tcscontrol.control_backend.maintenance.impl.mapper.MaintenanceMapper;
import com.tcscontrol.control_backend.maintenance.model.dto.MaintenanceDTO;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.pessoa.fornecedor.Fornecedor;
import com.tcscontrol.control_backend.pessoa.fornecedor.FornecedorMapper;
import com.tcscontrol.control_backend.utilitarios.UtilControl;
import com.tcscontrol.control_backend.utilitarios.UtilData;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MaintenanceNegocioImpl implements MaintenanceNegocio{
      
      
      private MaintenanceRepository maintenanceRepository;
      private MaintenanceMapper maintenanceMapper;
      private FornecedorMapper fornecedorMapper;
      private PatrimonyMapper patrimonyMapper;
      
      @Override
      public List<MaintenanceDTO> list() {
            return maintenanceRepository.findAll()
            .stream()
            .map(maintenanceMapper::toDto)
            .collect(Collectors.toList());
      }

      @Override
      public MaintenanceDTO findById(Long id) {
            return maintenanceRepository.findById(id)
            .map(maintenanceMapper::toDto)
            .orElseThrow(()-> new RecordNotFoundException(id));
      }

      @Override
      public MaintenanceDTO create(MaintenanceDTO maintenanceDTO) {
           return maintenanceMapper.toDto(maintenanceRepository.save(maintenanceMapper.toEntity(maintenanceDTO)));
      }

      @Override
      public MaintenanceDTO update(Long id, MaintenanceDTO maintenanceDTO) {
            return maintenanceRepository.findById(id)
            .map(recordFound->{
            Patrimony patrimony = patrimonyMapper.toEntity(maintenanceDTO.patrimonio());
            Fornecedor fornecedor = fornecedorMapper.toEntity(maintenanceDTO.fornecedor());
            recordFound.setId(maintenanceDTO.id());
            recordFound.setTpManutencao(UtilControl.convertTypeMaintenanceValue(maintenanceDTO.tpManutencao()));
            recordFound.setDsMotivoManutencao(maintenanceDTO.dsMotivoManutencao());
            recordFound.setVlManutencao(maintenanceDTO.vlManutencao());
            recordFound.setDsObservacao(maintenanceDTO.dsObservacao());
            recordFound.setDtAgendamento(UtilData.toDate(maintenanceDTO.dtAgendamento(), UtilData.FORMATO_DDMMAA));
            recordFound.setDtEntrada(UtilData.toDate(maintenanceDTO.dtEntrada(), UtilData.FORMATO_DDMMAA));
            recordFound.setDtFim(UtilData.toDate(maintenanceDTO.dtFim(), UtilData.FORMATO_DDMMAA));            
            recordFound.setPatrimony(patrimony);
            recordFound.setFornecedor(fornecedor);
                  return maintenanceMapper.toDto(maintenanceRepository.save(recordFound));
            }).orElseThrow(()-> new RecordNotFoundException(id));
      }

      @Override
      public void delete(@NotNull @Positive Long id) {
           maintenanceRepository.delete(maintenanceRepository.findById(id).orElseThrow(()-> new RecordNotFoundException(id) ));
      }
      
}
