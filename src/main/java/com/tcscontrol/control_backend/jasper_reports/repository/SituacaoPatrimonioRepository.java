package com.tcscontrol.control_backend.jasper_reports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;

public interface SituacaoPatrimonioRepository extends JpaRepository<Patrimony, Long>{
    
    @Query("SELECT p.tp_situacao FROM patrimonios p ORDER BY p.tp_situacao ASC")
    List<String> findSituacaoPatrimonio();
}
