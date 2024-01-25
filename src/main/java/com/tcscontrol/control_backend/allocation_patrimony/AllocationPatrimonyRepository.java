package com.tcscontrol.control_backend.allocation_patrimony;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcscontrol.control_backend.allocation_patrimony.model.entity.AllocationPatrimony;

public interface AllocationPatrimonyRepository extends JpaRepository<AllocationPatrimony, Long> {

        AllocationPatrimony findByPatrimonyIdAndDtAlocacaoIsNotNullAndDtDevolucaoIsNull(Long id);

        AllocationPatrimony findByPatrimonyIdAndDtDevolucaoIsNull(Long id);

        @Query("SELECT ap " + //
                        "FROM AllocationPatrimony ap " + //
                        "JOIN FETCH ap.patrimony p " + //
                        "WHERE ap.patrimony.id IN (:ids) AND ap.status = 'Ativo'")
        List<AllocationPatrimony> getList(List<Long> ids);

        @Query("SELECT ap " + //
                        "FROM AllocationPatrimony ap " + //
                        "JOIN FETCH ap.patrimony p " + //
                        "WHERE ap.patrimony.id = :id AND ap.status = 'Ativo'")
        AllocationPatrimony pesquisAllocationPatrimonyPorIdPatrimonio(Long id);

        @Query("SELECT ap " + //
                        "FROM AllocationPatrimony ap " + //
                        "WHERE ap.status = 'Ativo'")
        List<AllocationPatrimony> getList();

        @Query("SELECT ap " + //
                        "FROM AllocationPatrimony ap " + //
                        "WHERE ap.status = 'Ativo'")
        List<AllocationPatrimony> getListPatrimonioDepartamento();

        @Query("SELECT ap " + //
                        "FROM AllocationPatrimony ap " + //
                        "WHERE ap.status = 'Ativo' AND ap.dtAlocacao BETWEEN :dtInicio AND :dtFim")
        List<AllocationPatrimony> getListPatrimonioDepartamento(Date dtInicio, Date dtFim);

}
