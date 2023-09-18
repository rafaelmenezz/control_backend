package com.tcscontrol.control_backend.patrimony.model.dto;

import java.util.List;

import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;
import com.tcscontrol.control_backend.warranty.model.dto.WarrantyDTO;

public record PatrimonyDTO (
    Long id, 
    Integer nrSerie,
    String nmPatrimonio,
    String nmDescricao,
    String nrCnpj,
    String nmFornecedor,
    Integer nrNF,
    String  dtNF,
    String dtAquisicao,
    Double vlAquisicao,
    Boolean fixo,
    List<WarrantyDTO> warranties,
    DepartmentDTO actualDepartment
){}
