package com.tcscontrol.control_backend.patrimonyconstruction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.patrimonyconstruction.model.entity.Requests;

public interface RequestsRepository extends JpaRepository<Requests, Long> {
      
}
