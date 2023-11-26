package com.tcscontrol.control_backend.maintenance;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcscontrol.control_backend.maintenance.model.entity.Maintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    @Query("FROM Maintenance m " +
            "WHERE m.dtEntrada >= :inicio " +
            "AND m.dtEntrada <= :fim " +
            "AND m.vlManutencao >= :vlMin " +
            "AND m.vlManutencao >= :vlMax " +
            "AND m.dtFim IS NOT NULL")
    List<Maintenance> getGastosManutencao(Date inicio, Date fim, Double vlMin, Double vlMax);

        @Query("FROM Maintenance m " +
            "WHERE m.dtEntrada >= :inicio " +
            "AND m.dtEntrada <= :fim " +
            "AND m.dtFim IS NOT NULL")
    List<Maintenance> getGastosManutencao(Date inicio, Date fim);

    @Query("FROM Maintenance m " +
            "WHERE m.vlManutencao >= :vlMin " +
            "AND m.vlManutencao >= :vlMax " +
            "AND m.dtFim IS NOT NULL")
    List<Maintenance> getGastosManutencao(Double vlMin, Double vlMax);

    @Query("FROM Maintenance m " +
            "WHERE m.dtFim IS NOT NULL")
    List<Maintenance> getGastosManutencao();

}
