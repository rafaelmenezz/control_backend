package com.tcscontrol.control_backend.allocation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tcscontrol.control_backend.allocation.model.dto.AllocationDTO;

@Service
public interface AllocationService {
      
      List<AllocationDTO> list();

      AllocationDTO findById(Long id);

      AllocationDTO create(AllocationDTO allocationDTO);

      AllocationDTO update(Long id, AllocationDTO allocationDTO);
}
