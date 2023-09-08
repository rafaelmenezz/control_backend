package com.tcscontrol.control_backend.warranty;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tcscontrol.control_backend.warranty.model.dto.WarrantyDTO;

@Service
public interface WarrantyService {

      List<WarrantyDTO> list();

      WarrantyDTO findById(Long id);

      WarrantyDTO create(WarrantyDTO warrantyDTO);

      WarrantyDTO update (Long id, WarrantyDTO warrantyDTO);
}
