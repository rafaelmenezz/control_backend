package com.tcscontrol.control_backend.allocation_patrimony;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.allocation_patrimony.model.entity.AllocationPatrimony;

public interface AllocationPatrimonyRepository extends JpaRepository<AllocationPatrimony, Long> {

    AllocationPatrimony findByPatrimonyIdAndDtAlocacaoIsNotNullAndDtDevolucaoIsNull(Long id);
    
}
