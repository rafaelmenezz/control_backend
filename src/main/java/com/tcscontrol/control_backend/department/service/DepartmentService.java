package com.tcscontrol.control_backend.department.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public interface DepartmentService {

    public List<DepartmentDTO> list();

    public DepartmentDTO findById(@PathVariable @NotNull Long id);

    public DepartmentDTO create(@Valid DepartmentDTO departmentDTO);

    public DepartmentDTO update(Long id, @Valid DepartmentDTO department);

    public void delete(@PathVariable @NotNull @Positive Long id);
    
}
