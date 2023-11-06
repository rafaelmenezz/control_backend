package com.tcscontrol.control_backend.maintenance.impl.mapper;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.maintenance.model.dto.MaintenanceDTO;
import com.tcscontrol.control_backend.maintenance.model.dto.MaintenancePatrimonyDTO;
import com.tcscontrol.control_backend.maintenance.model.entity.Maintenance;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.pessoa.fornecedor.Fornecedor;
import com.tcscontrol.control_backend.utilitarios.UtilControl;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MaintenanceMapper {

      public MaintenanceDTO toDto(Maintenance maintenance, PatrimonyDTO patrimonyDTO){
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
                  maintenance.getMaintenanceStatus().getValue(),
                  maintenance.getFornecedor().getNmName(),
                  maintenance.getFornecedor().getNrCnpj(),
                  patrimonyDTO);
      }

      public MaintenancePatrimonyDTO toMaintenancePatrimonyDTO(Maintenance maintenance){
            if(maintenance == null){
                  return null;
            }

            return new MaintenancePatrimonyDTO(
                  maintenance.getId(), 
                  UtilObjeto.isNotEmpty(maintenance.getTpManutencao()) ? maintenance.getTpManutencao().getValue() : null, 
                  maintenance.getDsMotivoManutencao(), 
                  maintenance.getVlManutencao(), 
                  maintenance.getDsObservacao(), 
                  UtilData.toString(maintenance.getDtAgendamento(), UtilData.FORMATO_DDMMAA), 
                  UtilData.toString(maintenance.getDtEntrada(), UtilData.FORMATO_DDMMAA), 
                  UtilData.toString(maintenance.getDtFim(), UtilData.FORMATO_DDMMAA),
                  maintenance.getFornecedor().getNmName(),
                  maintenance.getFornecedor().getNrCnpj());
      }

      public Maintenance toEntity(MaintenanceDTO maintenanceDTO, Fornecedor fornecedor, Patrimony patrimony){
            if (maintenanceDTO == null) {
                  return null;
            }
            Maintenance maintenance = new Maintenance();
            if (maintenanceDTO.id() != null) {
                  maintenance.setId(maintenanceDTO.id());
            }
            maintenance.setTpManutencao(UtilControl.convertTypeMaintenanceValue(maintenanceDTO.nmTypeMaintence()));
            maintenance.setDsMotivoManutencao(maintenanceDTO.dsMaintence());
            maintenance.setVlManutencao(maintenanceDTO.vlMaintence());
            maintenance.setDsObservacao(maintenanceDTO.observation());
            maintenance.setDtAgendamento(UtilData.toDate(maintenanceDTO.dtPrevisionMaintence(), UtilData.FORMATO_DDMMAA));
            maintenance.setDtEntrada(UtilData.toDate(maintenanceDTO.dtStartMaintence(), UtilData.FORMATO_DDMMAA));
            maintenance.setDtFim(UtilData.toDate(maintenanceDTO.dtEndMaintence(), UtilData.FORMATO_DDMMAA));            
            maintenance.setPatrimony(patrimony);
            maintenance.setFornecedor(fornecedor);

            return maintenance;
      }
}
