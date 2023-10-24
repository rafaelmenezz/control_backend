package com.tcscontrol.control_backend.patrimony.model.dto;

import java.util.List;

import com.tcscontrol.control_backend.constructions.model.dto.ConstructionDTO;
import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;
import com.tcscontrol.control_backend.maintenance.model.dto.MaintenanceDTO;
import com.tcscontrol.control_backend.warranty.model.dto.WarrantyDTO;

public record PatrimonyDTO (
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
    DepartmentDTO actualDepartment,
    ConstructionDTO actualConstruction,
    MaintenanceDTO actualMaintenance
){}
