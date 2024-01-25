package com.tcscontrol.control_backend.request_patrimony;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcscontrol.control_backend.request_patrimony.model.entity.RequestPatrimony;

public interface RequestPatrimonyRepository extends JpaRepository<RequestPatrimony, Long> {

        @Query("SELECT rp " + //
                        "FROM RequestPatrimony rp " + //
                        "JOIN FETCH rp.patrimony p " + //
                        "WHERE rp.patrimony.id IN (:ids) AND rp.status = 'Ativo'")
        List<RequestPatrimony> getList(List<Long> ids);

        @Query("SELECT rp " + //
                        "FROM RequestPatrimony rp " + //
                        "WHERE rp.status = 'Ativo'")
        List<RequestPatrimony> getList();

        @Query("SELECT rp " + //
                        "FROM RequestPatrimony rp " + //
                        "WHERE rp.status = 'Ativo' AND rp.dtRetirada IS NOT NULL")
        List<RequestPatrimony> getListPatrimonioObras();

        @Query("SELECT rp " + //
                        "FROM RequestPatrimony rp " + //
                        "WHERE rp.status = 'Ativo' AND rp.dtRetirada BETWEEN :dtInicio AND :dtFim")
        List<RequestPatrimony> getListPatrimonioObras(Date dtInicio, Date dtFim);

        @Query("SELECT rp " + //
                        "FROM RequestPatrimony rp " + //
                        "WHERE rp.status = 'Ativo' AND rp.dtRetirada IS NULL AND rp.dtPrevisaoRetirada BETWEEN :dtInicio AND :dtFim")
        List<RequestPatrimony> getListRequisicoesPendentes(Date dtInicio, Date dtFim);

        @Query("SELECT rp " + //
                        "FROM RequestPatrimony rp " + //
                        "WHERE rp.status = 'Ativo' AND rp.dtRetirada IS NULL AND rp.dtPrevisaoRetirada IS NOT NULL")
        List<RequestPatrimony> getListRequisicoesPendentes();
}
