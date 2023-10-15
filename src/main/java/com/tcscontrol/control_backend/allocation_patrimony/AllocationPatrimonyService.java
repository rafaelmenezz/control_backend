package com.tcscontrol.control_backend.allocation_patrimony;

import org.springframework.stereotype.Service;

import com.tcscontrol.control_backend.allocation.model.dto.AllocationDTO;

@Service
public interface AllocationPatrimonyService {
    
    AllocationDTO create(AllocationDTO allocationDTO);

    AllocationDTO update(AllocationDTO allocationDTO);
}
