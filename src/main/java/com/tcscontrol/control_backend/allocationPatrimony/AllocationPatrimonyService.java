package com.tcscontrol.control_backend.allocationPatrimony;

import org.springframework.stereotype.Service;

import com.tcscontrol.control_backend.allocationPatrimony.model.entity.AllocationPatrimony;

import jakarta.transaction.Transactional;

@Service
public class AllocationPatrimonyService {

    private final AllocationPatrimonyRepository allocationPatrimonyRepository;

    public AllocationPatrimonyService(AllocationPatrimonyRepository allocationPatrimonyRepository) {
        this.allocationPatrimonyRepository = allocationPatrimonyRepository;
    }

    @Transactional
    public AllocationPatrimony saveAllocationPatrimony(AllocationPatrimony allocationPatrimony) {
        return allocationPatrimonyRepository.save(allocationPatrimony);
    }

}
