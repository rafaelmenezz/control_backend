package com.tcscontrol.control_backend.pessoa.fornecedor;

import org.springframework.stereotype.Component;

@Component
public interface FornecedorNegocio extends FornecedorService{
    
    Fornecedor pesquisaFornecedorCnpj(String nrCnpj);

    Fornecedor cadastrarFornecedor(Fornecedor fornecedor);
}
