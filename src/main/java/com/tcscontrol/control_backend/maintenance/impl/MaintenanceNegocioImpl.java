package com.tcscontrol.control_backend.maintenance.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation_patrimony.AllocationPatrimonyNegocio;
import com.tcscontrol.control_backend.allocation_patrimony.model.entity.AllocationPatrimony;
import com.tcscontrol.control_backend.enuns.MaintenanceStatus;
import com.tcscontrol.control_backend.enuns.SituationType;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.enviar_email.EmailNegocio;
import com.tcscontrol.control_backend.exception.IllegalRequestException;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.maintenance.MaintenanceNegocio;
import com.tcscontrol.control_backend.maintenance.MaintenanceRepository;
import com.tcscontrol.control_backend.maintenance.impl.mapper.MaintenanceMapper;
import com.tcscontrol.control_backend.maintenance.model.dto.MaintenanceDTO;
import com.tcscontrol.control_backend.maintenance.model.entity.Maintenance;
import com.tcscontrol.control_backend.patrimony.PatrimonyNegocio;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.pessoa.fornecedor.Fornecedor;
import com.tcscontrol.control_backend.pessoa.fornecedor.FornecedorNegocio;
import com.tcscontrol.control_backend.utilitarios.UtilControl;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;
import com.tcscontrol.control_backend.utilitarios.UtilString;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MaintenanceNegocioImpl implements MaintenanceNegocio {

      private MaintenanceRepository maintenanceRepository;
      private MaintenanceMapper maintenanceMapper;
      private FornecedorNegocio fornecedorNegocio;
      private PatrimonyNegocio patrimonyNegocio;
      private AllocationPatrimonyNegocio allocationPatrimonyNegocio;
      private EmailNegocio emailNegocio;

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
                        .orElseThrow(() -> new RecordNotFoundException(id));
      }

      @Override
      public MaintenanceDTO create(MaintenanceDTO maintenanceDTO) {
            Fornecedor f = obtemFornecedor(maintenanceDTO.nmFornecedor(), maintenanceDTO.nrCnpj());
            Patrimony p = patrimonyNegocio.toEntity(maintenanceDTO.patrimony());
            Maintenance maintenance = maintenanceMapper.toEntity(maintenanceDTO, f, p);
            f.getMaintenances().add(maintenance);
            maintenance = maintenanceRepository.save(maintenance);
            fornecedorNegocio.cadastrarFornecedor(f);

            emailNegocio.enviarEmailAgendaManutencao(maintenance);
            return maintenanceMapper.toDto(maintenance,
                        patrimonyNegocio.toDTO(p));
      }

      @Override
      public MaintenanceDTO update(Long id, MaintenanceDTO maintenanceDTO) {
            return maintenanceRepository.findById(id)
                        .map(recordFound -> {
                              Patrimony patrimony = patrimonyNegocio.toEntity(maintenanceDTO.patrimony());
                              Fornecedor fornecedor = fornecedorNegocio.obtemFornecedor(maintenanceDTO.nrCnpj());
                              fornecedor.setNmName(maintenanceDTO.nmFornecedor());
                              fornecedor = fornecedorNegocio.cadastrarFornecedor(fornecedor);
                              recordFound.setId(maintenanceDTO.id());
                              recordFound.setTpManutencao(
                                          UtilControl.convertTypeMaintenanceValue(maintenanceDTO.nmTypeMaintence()));
                              recordFound.setDsMotivoManutencao(maintenanceDTO.dsMaintence());
                              recordFound.setVlManutencao(maintenanceDTO.vlMaintence());
                              recordFound.setDsObservacao(maintenanceDTO.observation());
                              recordFound.setDtAgendamento(UtilData.toDate(maintenanceDTO.dtPrevisionMaintence(),
                                          UtilData.FORMATO_DDMMAA));
                              recordFound.setDtEntrada(
                                          UtilData.toDate(maintenanceDTO.dtStartMaintence(), UtilData.FORMATO_DDMMAA));
                              recordFound.setDtFim(
                                          UtilData.toDate(maintenanceDTO.dtEndMaintence(), UtilData.FORMATO_DDMMAA));
                              recordFound.setPatrimony(patrimony);
                              recordFound.setFornecedor(fornecedor);
                              return maintenanceMapper.toDto(maintenanceRepository.save(recordFound),
                                          patrimonyNegocio.toDTO(patrimony));
                        }).orElseThrow(() -> new RecordNotFoundException(id));
      }

      @Override
      public MaintenanceDTO toSchedule(Long id, MaintenanceDTO maintenanceDTO) {
            validarItem(maintenanceDTO.dtPrevisionMaintence(), EXCEPTION_MSG_ERRO_DATA_AGENDAMENTO_INVALIDA);
            Fornecedor fornecedor = obtemFornecedor(maintenanceDTO.nmFornecedor(), maintenanceDTO.nrCnpj());
            Patrimony patrimony = patrimonyNegocio.toEntity(maintenanceDTO.patrimony());
            Maintenance maintenance = maintenanceMapper.toEntity(maintenanceDTO, fornecedor, patrimony);
            maintenance.setMaintenanceStatus(MaintenanceStatus.AGENDADA);
            maintenance = alterar(id, maintenance);
            patrimony = atualizarPatrimonio(patrimony);
            return maintenanceMapper.toDto(maintenance, patrimonyNegocio.toDTO(maintenance.getPatrimony()));
      }

      @Override
      public MaintenanceDTO toExecute(Long id, MaintenanceDTO maintenanceDTO) {
            validarItem(maintenanceDTO.dtStartMaintence(), EXCEPTION_MSG_ERRO_DATA_INICIAL_INVALIDA);
            Fornecedor fornecedor = obtemFornecedor(maintenanceDTO.nmFornecedor(), maintenanceDTO.nrCnpj());
            Patrimony patrimony = patrimonyNegocio.toEntity(maintenanceDTO.patrimony());
            patrimony.setTpSituacao(SituationType.EM_MANUTENCAO);
            Maintenance maintenance = maintenanceMapper.toEntity(maintenanceDTO, fornecedor, patrimony);
            maintenance.setMaintenanceStatus(MaintenanceStatus.EM_EXECUCAO);
            maintenance = alterar(id, maintenance);
            patrimony = atualizarPatrimonio(patrimony);
            emailNegocio.enviarEmailIniciarManutencao(maintenance);
            return maintenanceMapper.toDto(maintenance, patrimonyNegocio.toDTO(maintenance.getPatrimony()));
      }

      @Override
      public MaintenanceDTO toFinish(Long id, MaintenanceDTO maintenanceDTO) {
            validarItem(maintenanceDTO.dtEndMaintence(), EXCEPTION_MSG_ERRO_DATA_FINAL_INVALIDA);
            Fornecedor fornecedor = obtemFornecedor(maintenanceDTO.nmFornecedor(), maintenanceDTO.nrCnpj());
            Patrimony patrimony = patrimonyNegocio.toEntity(maintenanceDTO.patrimony());
            Maintenance maintenance = maintenanceMapper.toEntity(maintenanceDTO, fornecedor, patrimony);
            maintenance.setMaintenanceStatus(MaintenanceStatus.EXECUTADA);
            maintenance.setTpStatus(Status.INACTIVE);
            maintenance = alterar(id, maintenance);
            if (patrimony.getFixo()) {
                  patrimony.setTpSituacao(SituationType.ALOCADO);
            } else {
                  patrimony.setTpSituacao(SituationType.DISPONIVEL);
            }
            patrimony = atualizarPatrimonio(patrimony);
            emailNegocio.enviarEmailFinalizarManutencao(maintenance);
            return maintenanceMapper.toDto(maintenance, patrimonyNegocio.toDTO(maintenance.getPatrimony()));
      }

      @Override
      public void cancel(Long id, MaintenanceDTO maintenanceDTO) {
            validarItem(maintenanceDTO.observation(), EXCEPTION_MSG_ERRO_OBSERVATION_NOT_NULL);
            Fornecedor fornecedor = obtemFornecedor(maintenanceDTO.nmFornecedor(), maintenanceDTO.nrCnpj());
            Patrimony patrimony = patrimonyNegocio.toEntity(maintenanceDTO.patrimony());

            Maintenance maintenance = maintenanceMapper.toEntity(maintenanceDTO, fornecedor, patrimony);
            maintenance.setDtFim(new Date());
            maintenance.setTpStatus(Status.INACTIVE);
            maintenance.setMaintenanceStatus(MaintenanceStatus.CANCELADA);
            maintenance = alterar(id, maintenance);
            if (patrimony.getFixo() && patrimonioAlocado(patrimony.getId())) {
                  patrimony.setTpSituacao(SituationType.ALOCADO);
            } else {
                  patrimony.setTpSituacao(SituationType.DISPONIVEL);
            }
            String mensagemCancelamento = UtilString.isNotEmpty(maintenance.getDsObservacao()) ? maintenance.getDsObservacao() : UtilString.EMPTY;
            maintenance.setDsObservacao(mensagemCancelamento.concat(MSG_CANCELAMENTO_MANUTENCAO));
            patrimony = atualizarPatrimonio(patrimony);

            emailNegocio.enviarEmailCancelarManutencao(maintenance);
      }

      private Fornecedor obtemFornecedor(String nome, String cnpj) {
            Fornecedor f = fornecedorNegocio.pesquisaFornecedorCnpj(cnpj);

            if (UtilObjeto.isEmpty(f)) {
                  f = new Fornecedor();
                  f.setNmName(nome);
                  f.setNrCnpj(cnpj);
                  f = fornecedorNegocio.cadastrarFornecedor(f);
            }

            return f;
      }

      private Maintenance alterar(Long id, Maintenance maintenance) {
            return maintenanceRepository.findById(id)
                        .map(recordFound -> {
                              Patrimony patrimony = maintenance.getPatrimony();
                              Fornecedor fornecedor = maintenance.getFornecedor();
                              recordFound.setId(maintenance.getId());
                              recordFound.setTpManutencao(maintenance.getTpManutencao());
                              recordFound.setDsMotivoManutencao(maintenance.getDsMotivoManutencao());
                              recordFound.setVlManutencao(maintenance.getVlManutencao());
                              recordFound.setDsObservacao(maintenance.getDsObservacao());
                              recordFound.setDtAgendamento(maintenance.getDtAgendamento());
                              recordFound.setMaintenanceStatus(maintenance.getMaintenanceStatus());
                              recordFound.setTpStatus(maintenance.getTpStatus());
                              recordFound.setDtEntrada(
                                          maintenance.getDtEntrada());
                              recordFound.setDtFim(
                                          maintenance.getDtFim());
                              recordFound.setPatrimony(patrimony);
                              recordFound.setFornecedor(fornecedor);
                              return maintenanceRepository.save(recordFound);
                        }).orElseThrow(() -> new RecordNotFoundException(id));
      }

      private void validarItem(Object object, String mensagem) {
            if (UtilObjeto.isEmpty(object)) {
                  throw new IllegalRequestException(mensagem);
            }
      }

      private Patrimony atualizarPatrimonio(Patrimony patrimony) {
            return patrimonyNegocio.atualizaPatrimonio(patrimony);
      }

      private Boolean patrimonioAlocado(Long id) {
            AllocationPatrimony ap = allocationPatrimonyNegocio.pesquisaAllocationPatrimonyPorId(id);
            return UtilObjeto.isEmpty(ap) ? Boolean.FALSE : Boolean.TRUE;
      }

}
