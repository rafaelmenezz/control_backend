package com.tcscontrol.control_backend.maintenance.model.dto;

public record MaintenancePatrimonyDTO(
    Long id, 
    String nmTypeMaintence,
    String dsMaintence,
    Double vlMaintence,
    String observation,
    String dtPrevisionMaintence,
    String dtStartMaintence, 
    String dtEndMaintence,
    String nmFornecedor,
    String nrCnpj
) {}
