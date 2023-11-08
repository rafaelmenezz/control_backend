package com.tcscontrol.control_backend.allocationPatrimony.service.impl;

import org.springframework.stereotype.Service;

import com.tcscontrol.control_backend.allocationPatrimony.model.AllocationPatrimony;
import com.tcscontrol.control_backend.allocationPatrimony.repository.AllocationPatrimonyRepository;
import com.tcscontrol.control_backend.allocationPatrimony.service.AllocationPatrimonyService;

import jakarta.transaction.Transactional;

@Service
public class AllocationPatrimonyServiceImpl implements AllocationPatrimonyService {

    private final AllocationPatrimonyRepository allocationPatrimonyRepository;

    public AllocationPatrimonyServiceImpl(AllocationPatrimonyRepository allocationPatrimonyRepository) {
        this.allocationPatrimonyRepository = allocationPatrimonyRepository;
    }

    @Transactional
    public AllocationPatrimony save(AllocationPatrimony allocationPatrimony) {
        return allocationPatrimonyRepository.save(allocationPatrimony);
    }

}
