package com.tcscontrol.control_backend.allocation_patrimony;

import org.springframework.stereotype.Service;

import com.tcscontrol.control_backend.allocation.model.dto.AllocationDTO;
import com.tcscontrol.control_backend.allocation.model.dto.AllocationResponse;

@Service
public interface AllocationPatrimonyService {
    
    AllocationResponse create(AllocationDTO allocationDTO);

    AllocationResponse update(Long id, AllocationDTO allocationDTO);
}
