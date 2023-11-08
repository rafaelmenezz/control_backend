package com.tcscontrol.control_backend.allocation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcscontrol.control_backend.allocation.model.entity.Allocation;

public interface AllocationRepository extends JpaRepository<Allocation, Long> {
    
}
