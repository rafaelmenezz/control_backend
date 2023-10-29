package com.tcscontrol.control_backend.maintenance.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.maintenance.MaintenanceNegocio;
import com.tcscontrol.control_backend.maintenance.MaintenanceRepository;
import com.tcscontrol.control_backend.maintenance.impl.mapper.MaintenanceMapper;
import com.tcscontrol.control_backend.maintenance.model.dto.MaintenanceDTO;
import com.tcscontrol.control_backend.patrimony.PatrimonyNegocio;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.pessoa.fornecedor.Fornecedor;
import com.tcscontrol.control_backend.pessoa.fornecedor.FornecedorNegocio;
import com.tcscontrol.control_backend.utilitarios.UtilControl;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MaintenanceNegocioImpl implements MaintenanceNegocio{
      
      
      private MaintenanceRepository maintenanceRepository;
      private MaintenanceMapper maintenanceMapper;
      private FornecedorNegocio fornecedorNegocio;
      private PatrimonyNegocio patrimonyNegocio;
      
      @Override
      public List<MaintenanceDTO> list() {
            return maintenanceRepository.findAll()
            .stream()
            .map(m -> maintenanceMapper.toDto(m, patrimonyNegocio.toDTO(m.getPatrimony())))
            .collect(Collectors.toList());
      }

      @Override
      public MaintenanceDTO findById(Long id) {
            return maintenanceRepository.findById(id)
            .map(m -> maintenanceMapper.toDto(m, patrimonyNegocio.toDTO(m.getPatrimony())))
            .orElseThrow(()-> new RecordNotFoundException(id));
      }

      @Override
      public MaintenanceDTO create(MaintenanceDTO maintenanceDTO) {
            Fornecedor f = obtemFornecedor(maintenanceDTO.nmFornecedor(), maintenanceDTO.nrCnpj());
            Patrimony p = patrimonyNegocio.toEntity(maintenanceDTO.patrimony());

           return maintenanceMapper.toDto(maintenanceRepository.save(maintenanceMapper.toEntity(maintenanceDTO, f, p)), patrimonyNegocio.toDTO(p));
      }

      @Override
      public MaintenanceDTO update(Long id, MaintenanceDTO maintenanceDTO) {
            return maintenanceRepository.findById(id)
            .map(recordFound->{
            Patrimony patrimony = patrimonyNegocio.toEntity(maintenanceDTO.patrimony());
            Fornecedor fornecedor = fornecedorNegocio.obtemFornecedor(maintenanceDTO.nrCnpj());
            fornecedor.setNmName(maintenanceDTO.nmFornecedor());
            fornecedor = fornecedorNegocio.cadastrarFornecedor(fornecedor);
            recordFound.setId(maintenanceDTO.id());
            recordFound.setTpManutencao(UtilControl.convertTypeMaintenanceValue(maintenanceDTO.nmTypeMaintence()));
            recordFound.setDsMotivoManutencao(maintenanceDTO.dsMaintence());
            recordFound.setVlManutencao(maintenanceDTO.vlMaintence());
            recordFound.setDsObservacao(maintenanceDTO.observation());
            recordFound.setDtAgendamento(UtilData.toDate(maintenanceDTO.dtPrevisionMaintence(), UtilData.FORMATO_DDMMAA));
            recordFound.setDtEntrada(UtilData.toDate(maintenanceDTO.dtStartMaintence(), UtilData.FORMATO_DDMMAA));
            recordFound.setDtFim(UtilData.toDate(maintenanceDTO.dtEndMaintence(), UtilData.FORMATO_DDMMAA));            
            recordFound.setPatrimony(patrimony);
            recordFound.setFornecedor(fornecedor);
                  return maintenanceMapper.toDto(maintenanceRepository.save(recordFound), patrimonyNegocio.toDTO(patrimony));
            }).orElseThrow(()-> new RecordNotFoundException(id));
      }

      @Override
      public MaintenanceDTO toSchedule(Long id, MaintenanceDTO maintenanceDTO) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'toSchedule'");
      }

      @Override
      public MaintenanceDTO toExecute(Long id, MaintenanceDTO maintenanceDTO) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'toExecute'");
      }

      @Override
      public MaintenanceDTO toFinish(Long id, MaintenanceDTO maintenanceDTO) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'toFinish'");
      }

      @Override
      public void cancel(Long id, MaintenanceDTO maintenanceDTO) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'cancel'");
      }

      private Fornecedor obtemFornecedor(String nome, String cnpj){
            Fornecedor f = fornecedorNegocio.obtemFornecedor(cnpj);

            if (UtilObjeto.isEmpty(f)) {
                  f = new Fornecedor();
                  f.setNmName(nome);
                  f.setNrCnpj(cnpj);
                  fornecedorNegocio.cadastrarFornecedor(f);
                  return f;
            }

            return f;
      }

      
}
