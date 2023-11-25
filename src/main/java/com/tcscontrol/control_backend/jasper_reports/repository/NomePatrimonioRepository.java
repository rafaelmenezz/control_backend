package com.tcscontrol.control_backend.jasper_reports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;

public interface NomePatrimonioRepository extends JpaRepository<Patrimony, Long>{

    @Query("SELECT p.nm_patrimonio FROM patrimonios p ORDER BY p.nm_patrimonio ASC")
    List<String> findNomePatrimonio();
    
}
