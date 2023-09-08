package com.tcscontrol.control_backend.patrimony;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;

public interface PatrimonyRepository extends JpaRepository<Patrimony, Long> {
    
    List<Patrimony> findAll(Specification<Patrimony> specification);

    List<Patrimony> findByNmPatrimonioContainingOrNrSerieContainingOrDsPatrimonioContainingOrFornecedorNrCnpjContainingOrFornecedorNmNameContainingOrDtAquisicaoContaining(
        String nmPatrimonio,
        Integer nrSerie,
        String dsPatrimonio,
        String nrCnpj,
        String nmFornecedor,
        Date dtAquisicao
    );
}
