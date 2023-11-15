package com.tcscontrol.control_backend.patrimony;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.patrimony.model.entity.LossTheft;

public interface LossTheftRepository extends JpaRepository<LossTheft ,Long> {
    
}
