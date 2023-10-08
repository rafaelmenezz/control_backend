package com.tcscontrol.control_backend.allocation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tcscontrol.control_backend.allocation.model.dto.AllocationDTO;
import com.tcscontrol.control_backend.allocation.model.dto.AllocationResponse;

@Service
public interface AllocationService {
      
      List<AllocationResponse> list();

      AllocationResponse findById(Long id);

      AllocationResponse create(AllocationDTO allocationDTO);

      AllocationResponse update(Long id, AllocationDTO allocationDTO);
      
}
