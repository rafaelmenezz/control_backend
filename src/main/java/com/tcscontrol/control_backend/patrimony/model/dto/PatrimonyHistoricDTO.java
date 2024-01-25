package com.tcscontrol.control_backend.patrimony.model.dto;

import java.util.List;
import com.tcscontrol.control_backend.warranty.model.dto.WarrantyDTO;

public record PatrimonyHistoricDTO(
    Long id, 
    String nrSerie,
    String nmPatrimonio,
    String nmDescricao,
    String nrCnpj,
    String nmFornecedor,
    Integer nrNF,
    String  dtNF,
    String dtAquisicao,
    Double vlAquisicao,
    Boolean fixo,
    String situacao,
    List<WarrantyDTO> warranties,
    List<PatrimonyAllocationDTO> historyDepartment,
    List<PatrimonyConstructionDTO> historyConstruction,
    List<PatrimonyMaintenanceDTO> historylMaintenance, 
    LossTheftDTO lossTheft,
    String Status
) {}
