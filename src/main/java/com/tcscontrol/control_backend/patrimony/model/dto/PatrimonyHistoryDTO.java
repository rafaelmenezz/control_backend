package com.tcscontrol.control_backend.patrimony.model.dto;

import java.util.List;

import com.tcscontrol.control_backend.constructions.model.dto.ConstructionDTO;
import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;
import com.tcscontrol.control_backend.maintenance.model.dto.MaintenancePatrimonyDTO;
import com.tcscontrol.control_backend.warranty.model.dto.WarrantyDTO;

public record PatrimonyHistoryDTO(
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
    List<DepartmentDTO> historyDepartment,
    ConstructionDTO historyConstruction,
    List<MaintenancePatrimonyDTO> historylMaintenance, 
    LossTheftDTO lossTheft,
    String Status
) {}
