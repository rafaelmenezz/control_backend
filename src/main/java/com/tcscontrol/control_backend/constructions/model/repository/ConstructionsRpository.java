package com.tcscontrol.control_backend.constructions.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.constructions.model.entity.Constructions;

public interface ConstructionsRpository extends JpaRepository<Constructions, Long>{

    Constructions findBynmObra(String obra);
    
}