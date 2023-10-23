package com.tcscontrol.control_backend.patrimony;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;

public interface PatrimonyRepository extends JpaRepository<Patrimony, Long> {
    
    List<Patrimony> findAll(Specification<Patrimony> specification);

    List<Patrimony> findByNmPatrimonioContainingOrNrSerieContainingOrNmDescricaoContainingOrFornecedorNrCnpjContainingOrFornecedorNmNameContainingOrDtAquisicaoContaining(
        String nmPatrimonio,
        String nrSerie,
        String nmDescricao,
        String nrCnpj,
        String nmFornecedor,
        Date dtAquisicao
    );

    @Query("FROM Patrimony p WHERE p.nmPatrimonio ilike %:nome% AND p.fixo = :fixo AND p.tpSituacao = 'Disponivel' ")
    List<Patrimony> findPatrimoniesToAllocation(@Param("nome") String nmPatrimony, @Param("fixo") Boolean fixo);


}
