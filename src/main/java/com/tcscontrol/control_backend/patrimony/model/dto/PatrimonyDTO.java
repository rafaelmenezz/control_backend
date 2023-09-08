package com.tcscontrol.control_backend.patrimony.model.dto;

import com.tcscontrol.control_backend.pessoa.fornecedor.FornecedorDTO;

public record PatrimonyDTO (
    Long id, 
    Integer nrSerie,
    String nmPatrimonio,
    String dsPatrimonio,
    Integer nrNotaFiscal,
    String  dtNotaFiscal,
    String dtAquisicao,
    Double vlAquisicao,
    Boolean fixo,
    String status,
    FornecedorDTO fornecedor
){}
