package com.tcscontrol.control_backend.pessoa.fornecedor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface FornecedorService {

    List<FornecedorDTO> list();

    FornecedorDTO findById(Long id);

    FornecedorDTO create(FornecedorDTO fornecedorDTO);

    FornecedorDTO update(Long id, FornecedorDTO fornecedorDTO);

    void delete(Long id);
    
}
