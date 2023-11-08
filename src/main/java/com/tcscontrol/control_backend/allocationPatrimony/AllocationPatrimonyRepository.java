package com.tcscontrol.control_backend.allocationPatrimony;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcscontrol.control_backend.allocationPatrimony.model.entity.AllocationPatrimony;

@Repository
public interface AllocationPatrimonyRepository extends JpaRepository<AllocationPatrimony, Long> {
}