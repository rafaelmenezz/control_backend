package com.tcscontrol.control_backend.maintenance.model.dto;

import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.pessoa.fornecedor.FornecedorDTO;

public record MaintenanceDTO(
      Long id, 
      String tpManutencao,
      String dsMotivoManutencao,
      Double vlManutencao,
      String dsObservacao,
      String dtAgendamento,
      String dtEntrada, 
      String dtFim,
      PatrimonyDTO patrimonio,
      FornecedorDTO fornecedor ){}
