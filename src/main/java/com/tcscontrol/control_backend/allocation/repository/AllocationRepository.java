package com.tcscontrol.control_backend.allocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcscontrol.control_backend.allocation.model.Allocation;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {
}