package com.tcscontrol.control_backend.department;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;
import com.tcscontrol.control_backend.department.model.entity.Department;

@Component
public interface DepartmentNegocio extends DepartmentService{

    DepartmentDTO tDto(Department department);

    Department toEntity(DepartmentDTO departmentDTO);
    
}
