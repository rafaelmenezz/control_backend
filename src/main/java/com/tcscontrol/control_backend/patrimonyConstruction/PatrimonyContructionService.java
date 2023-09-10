package com.tcscontrol.control_backend.patrimonyConstruction;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tcscontrol.control_backend.patrimonyConstruction.model.dto.PatrimonyContructionDTO;

@Service
public interface PatrimonyContructionService {
      
      List<PatrimonyContructionDTO> list();

      PatrimonyContructionDTO findById(Long id);

      PatrimonyContructionDTO create(PatrimonyContructionDTO allocationDTO);

      PatrimonyContructionDTO update(Long id, PatrimonyContructionDTO allocationDTO);
}
