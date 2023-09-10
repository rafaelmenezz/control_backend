package com.tcscontrol.control_backend.department;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcscontrol.control_backend.department.model.dto.DepartmentCreateDTO;
import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public interface DepartmentService {
    
    public List<DepartmentDTO> list();

    public DepartmentDTO findById(Long id);

    public DepartmentDTO create(DepartmentCreateDTO departmentCreateDTO);

    public DepartmentDTO update(DepartmentCreateDTO departmentCreateDTO);

    public void delete(@PathVariable @NotNull @Positive Long id);
}
