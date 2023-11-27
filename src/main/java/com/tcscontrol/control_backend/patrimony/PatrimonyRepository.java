package com.tcscontrol.control_backend.patrimony;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;

public interface PatrimonyRepository extends JpaRepository<Patrimony, Long> {

    List<Patrimony> findAll();

    List<Patrimony> findByTpStatus(Status tpStatus);

    List<Patrimony> findByNmPatrimonioContainingOrNrSerieContainingOrNmDescricaoContainingOrFornecedorNrCnpjContainingOrFornecedorNmNameContaining(
            String nmPatrimonio,
            String nrSerie,
            String nmDescricao,
            String nrCnpj,
            String nmFornecedor);

    @Query("FROM Patrimony p WHERE p.nmPatrimonio ilike %:nome% AND p.fixo = :fixo AND (p.tpSituacao = 'Disponivel' OR p.tpSituacao = 'Alocado')")
    List<Patrimony> findPatrimoniesToAllocation(@Param("nome") String nmPatrimony, @Param("fixo") Boolean fixo);

    @Query("FROM Patrimony p WHERE p.id = :id AND p.fixo = false AND p.tpSituacao = 'Disponivel'")
    Optional<Patrimony> findByIdPatrimonyToConstruction(@Param("id") Long id);

    @Query("FROM Patrimony p WHERE p.id = :id AND p.fixo = false AND (p.tpSituacao = 'Disponivel' OR p.tpSituacao = 'Alocado')")
    Optional<Patrimony> findByIdPatrimonyToAllocation(@Param("id") Long id);

    @Query("FROM Patrimony p WHERE p.fixo = :fixo AND p.tpSituacao = 'Disponivel'")
    List<Patrimony> getPatrimoniosDisponivel(Boolean fixo);

    @Query("FROM Patrimony p WHERE p.tpSituacao = 'Disponivel'")
    List<Patrimony> getPatrimoniosDisponivel();

    @Query("FROM Patrimony p WHERE p.nmPatrimonio LIKE %:nome% AND p.nrSerie LIKE %:nrSerie%")
    List<Patrimony> getPatrimonies(String nome, String nrSerie);

     @Query("FROM Patrimony p WHERE p.tpStatus = 'Ativo'")
    List<Patrimony> getPatrimonies();

    @Query("FROM Patrimony p WHERE p.tpStatus = 'Ativo' AND p.dtAquisicao BETWEEN :dtInicio AND :dtFim ")
    List<Patrimony> getPatrimonies(Date dtInicio, Date dtFim);

}
