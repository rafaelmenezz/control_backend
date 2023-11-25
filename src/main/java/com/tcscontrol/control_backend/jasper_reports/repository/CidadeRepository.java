package com.tcscontrol.control_backend.jasper_reports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcscontrol.control_backend.constructions.model.entity.Construction;

public interface CidadeRepository extends JpaRepository<Construction, Long>{

    @Query("SELECT c.nm_cidade FROM obras c ORDER BY c.nm_cidade ASC")
    List<String> findCidades();
    
}
