package com.tcscontrol.control_backend.patrimony.model.dto;

import java.util.List;

import com.tcscontrol.control_backend.constructions.model.dto.ConstructionDTO;
import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;
import com.tcscontrol.control_backend.warranty.model.entity.Warranty;

public record PatrimonyResponse(
    String nmPatrimonio,
    String nmSerie,
    String nmDescricao,
    String nmCnpj,
    String nmFornecedor,
    String nmNF,
    String dtNF,
    String dtAquisicao,
    Double vlDescricao,
    Boolean fixo,
    List<Warranty> warranties,
    DepartmentDTO actualDepartment,
    ConstructionDTO actualConstructionDTO
){}
