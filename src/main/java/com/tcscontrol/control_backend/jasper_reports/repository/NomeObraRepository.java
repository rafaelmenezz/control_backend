package com.tcscontrol.control_backend.jasper_reports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcscontrol.control_backend.constructions.model.entity.Construction;

public interface NomeObraRepository extends JpaRepository<Construction, Long>{

    @Query("SELECT c.nm_obra FROM obras c ORDER BY c.nm_obra ASC")
    List<String> findNomeObra();
    
}
