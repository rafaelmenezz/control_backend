package com.tcscontrol.control_backend.constructions;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.constructions.model.entity.Construction;

public interface ConstructionRepository extends JpaRepository<Construction, Long> {
      
}
