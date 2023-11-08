package com.tcscontrol.control_backend.allocation.service;

import java.util.List;

import com.tcscontrol.control_backend.allocation.dto.AllocationDTO;
import com.tcscontrol.control_backend.allocation.dto.AllocationResponse;

public interface AllocationService {
      List<AllocationResponse> list();

      AllocationResponse findById(Long id);

      AllocationResponse create(AllocationDTO allocationDTO);
}
