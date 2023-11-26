package com.tcscontrol.control_backend.requests;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcscontrol.control_backend.pessoa.user.model.entity.User;
import com.tcscontrol.control_backend.request_patrimony.model.entity.RequestPatrimony;
import com.tcscontrol.control_backend.requests.model.entity.Requests;

public interface RequestsRepository extends JpaRepository<Requests, Long> {

        @Query("SELECT p " + //
                        "FROM Requests r " +
                        "INNER JOIN r.patrimonies p " +
                        "INNER JOIN p.patrimony pt " +
                        "WHERE (p.dtPrevisaoDevolucao = :data OR p.dtPrevisaoDevolucao = :hoje) AND pt.tpSituacao = 'Registrado' AND status = 'Ativo' GROUP BY p")
        List<RequestPatrimony> getPatrimoniosVencer(Date data, Date hoje);

        @Query("SELECT p " + //
                        "FROM Requests r " +
                        "INNER JOIN r.patrimonies p " +
                        "INNER JOIN p.patrimony pt " +
                        "WHERE p.dtPrevisaoDevolucao < :data AND pt.tpSituacao = 'Registrado' AND status = 'Ativo' GROUP BY p")
        List<RequestPatrimony> getPatrimoniosVencidos(Date data);

        @Query("SELECT u " + //
                        "FROM Requests r " +
                        "INNER JOIN r.patrimonies p " +
                        "INNER JOIN p.patrimony pt " +
                        "INNER JOIN r.construction c " +
                        "INNER JOIN c.user u " +
                        "WHERE (p.dtPrevisaoDevolucao = :data OR p.dtPrevisaoDevolucao = :hoje) AND pt.tpSituacao = 'Registrado' GROUP BY u")
        List<User> getUsuariosPatrimoniosVencer(Date data, Date hoje);

        @Query("SELECT u " + //
                        "FROM Requests r " +
                        "INNER JOIN r.patrimonies p " +
                        "INNER JOIN p.patrimony pt " +
                        "INNER JOIN r.construction c " +
                        "INNER JOIN c.user u " +
                        "WHERE p.dtPrevisaoDevolucao < :data AND pt.tpSituacao = 'Registrado' GROUP BY u")
        List<User> getUsuariosPatrimoniosVencidos(Date data);

        @Query("SELECT p " + //
                        "FROM Requests r " +
                        "INNER JOIN r.patrimonies p " +
                        "INNER JOIN p.patrimony pt " +
                        "WHERE p.dtPrevisaoDevolucao < :data AND pt.tpSituacao = 'Registrado' AND status = 'Ativo' GROUP BY p")
        List<RequestPatrimony> getDevolucoesVencidas(Date data);

}
