package com.tcscontrol.control_backend.maintenance.impl.mapper;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.maintenance.model.dto.MaintenanceDTO;
import com.tcscontrol.control_backend.maintenance.model.entity.Maintenance;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.pessoa.fornecedor.Fornecedor;
import com.tcscontrol.control_backend.pessoa.fornecedor.FornecedorMapper;
import com.tcscontrol.control_backend.utilitarios.UtilControl;
import com.tcscontrol.control_backend.utilitarios.UtilData;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MaintenanceMapper {

      private PatrimonyMapper patrimonyMapper;
      private FornecedorMapper fornecedorMapper;      

      public MaintenanceDTO toDto(Maintenance maintenance){
            if(maintenance == null){
                  return null;
            }

            return new MaintenanceDTO(
                  maintenance.getId(), 
                  maintenance.getTpManutencao().getValue(), 
                  maintenance.getDsMotivoManutencao(), 
                  maintenance.getVlManutencao(), 
                  maintenance.getDsObservacao(), 
                  UtilData.toString(maintenance.getDtAgendamento(), UtilData.FORMATO_DDMMAA), 
                  UtilData.toString(maintenance.getDtEntrada(), UtilData.FORMATO_DDMMAA), 
                  UtilData.toString(maintenance.getDtFim(), UtilData.FORMATO_DDMMAA), 
                  patrimonyMapper.toDto(maintenance.getPatrimony()), 
                  fornecedorMapper.toDTO(maintenance.getFornecedor()));
      }

      public Maintenance toEntity(MaintenanceDTO maintenanceDTO){
            if (maintenanceDTO == null) {
                  return null;
            }
            Maintenance maintenance = new Maintenance();
            if (maintenanceDTO.id() != null) {
                  maintenance.setId(maintenanceDTO.id());
            }
            Patrimony patrimony = patrimonyMapper.toEntity(maintenanceDTO.patrimonio());
            Fornecedor fornecedor = fornecedorMapper.toEntity(maintenanceDTO.fornecedor());

            maintenance.setId(maintenanceDTO.id());
            maintenance.setTpManutencao(UtilControl.convertTypeMaintenanceValue(maintenanceDTO.tpManutencao()));
            maintenance.setDsMotivoManutencao(maintenanceDTO.dsMotivoManutencao());
            maintenance.setVlManutencao(maintenanceDTO.vlManutencao());
            maintenance.setDsObservacao(maintenanceDTO.dsObservacao());
            maintenance.setDtAgendamento(UtilData.toDate(maintenanceDTO.dtAgendamento(), UtilData.FORMATO_DDMMAA));
            maintenance.setDtEntrada(UtilData.toDate(maintenanceDTO.dtEntrada(), UtilData.FORMATO_DDMMAA));
            maintenance.setDtFim(UtilData.toDate(maintenanceDTO.dtFim(), UtilData.FORMATO_DDMMAA));            
            maintenance.setPatrimony(patrimony);
            maintenance.setFornecedor(fornecedor);

            return maintenance;
      }
}
