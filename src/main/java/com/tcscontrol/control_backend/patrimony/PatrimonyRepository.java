package com.tcscontrol.control_backend.patrimony;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;

public interface PatrimonyRepository extends JpaRepository<Patrimony, Long> {
    
    List<Patrimony> findAll(Specification<Patrimony> specification);

    List<Patrimony> findByNmPatrimonioContainingOrNrSerieOrNmDescricaoContainingOrFornecedorNrCnpjContainingOrFornecedorNmNameContainingOrDtAquisicaoContaining(
        String nmPatrimonio,
        Integer nrSerie,
        String nmDescricao,
        String nrCnpj,
        String nmFornecedor,
        Date dtAquisicao
    );

    @Query("SELECT a FROM Patrimony p JOIN p.allocations a WHERE p.fixo = false AND a.dtDevolucao IS NOT NULL")
    List<Patrimony> findPatrimoniesToAllocation();
}
