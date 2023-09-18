package com.tcscontrol.control_backend.pessoa.fornecedor;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>{

    List<Fornecedor> findByNrCnpj(String nrCnpj);

}