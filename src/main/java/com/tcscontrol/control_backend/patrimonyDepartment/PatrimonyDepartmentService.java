package com.tcscontrol.control_backend.patrimonyDepartment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tcscontrol.control_backend.patrimonyDepartment.model.dto.PatrimonyDepartmentDTO;

@Service
public interface PatrimonyDepartmentService {
      
      List<PatrimonyDepartmentDTO> list();

      PatrimonyDepartmentDTO findById(Long id);

      PatrimonyDepartmentDTO create(PatrimonyDepartmentDTO allocationDTO);

      PatrimonyDepartmentDTO update(Long id, PatrimonyDepartmentDTO allocationDTO);
}
