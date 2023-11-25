package com.tcscontrol.control_backend.jasper_reports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;

public interface TipoPatrimonioRepository extends JpaRepository<Patrimony, Long>{

    @Query("SELECT p.fl_fixo FROM Patrimony p ORDER BY p.fl_fixo ASC")
    List<String> findTipoPatrimonio();
    
}
