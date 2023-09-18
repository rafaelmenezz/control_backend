package com.tcscontrol.control_backend.allocation;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.allocation.model.entity.Allocation;

public interface AllocationRepository extends JpaRepository<Allocation, Long> {
    
    Allocation findByPatrimoniosIdAndDtDevolucaoIsNotNull(Long id);

}
