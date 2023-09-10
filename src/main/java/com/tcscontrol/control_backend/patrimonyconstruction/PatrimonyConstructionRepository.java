package com.tcscontrol.control_backend.patrimonyconstruction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.patrimonyconstruction.model.entity.PatrimonyConstruction;

public interface PatrimonyConstructionRepository extends JpaRepository<PatrimonyConstruction, Long> {
      
}
