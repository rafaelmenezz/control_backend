package com.tcscontrol.control_backend.maintenance.model.dto;

import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;

public record MaintenanceDTO(
      Long id, 
      String nmTypeMaintence,
      String dsMaintence,
      Double vlMaintence,
      String observation,
      String dtPrevisionMaintence,
      String dtStartMaintence, 
      String dtEndMaintence,
      String nmFornecedor,
      String nrCnpj,
      PatrimonyDTO patrimony ){}
