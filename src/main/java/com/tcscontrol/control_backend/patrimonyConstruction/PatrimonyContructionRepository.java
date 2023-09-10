package com.tcscontrol.control_backend.patrimonyConstruction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.patrimonyConstruction.model.entity.PatrimonyContruction;

public interface PatrimonyContructionRepository extends JpaRepository<PatrimonyContruction, Long> {
      
}
