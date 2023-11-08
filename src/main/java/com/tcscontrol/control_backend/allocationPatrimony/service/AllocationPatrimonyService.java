package com.tcscontrol.control_backend.allocationPatrimony.service;

import com.tcscontrol.control_backend.allocationPatrimony.model.AllocationPatrimony;

import jakarta.transaction.Transactional;

public interface AllocationPatrimonyService {

    @Transactional
    AllocationPatrimony save(AllocationPatrimony allocationPatrimony);

}