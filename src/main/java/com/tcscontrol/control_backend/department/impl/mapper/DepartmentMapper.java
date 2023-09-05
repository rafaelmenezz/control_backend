package com.tcscontrol.control_backend.department.impl.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DepartmentMapper {
    
    public DepartmentDTO toDTO(Department department) {
        if (department == null) {
            return null;
        }

        return new DepartmentDTO(
                department.getId(),
                department.getNmDepartamento(),
                department.getUser().getId());
        
    }

    public Department toEntity(DepartmentDTO departmentDTO){
        if(departmentDTO == null){
            return null;
        }

        Department department = new Department();
        if(departmentDTO.id() != null){
            department.setId(departmentDTO.id());
        }

        department.setId(departmentDTO.id());
        department.setNmDepartamento(departmentDTO.nmDepartamento());
        department.getUser().setId((departmentDTO.id_usuario()));

        return department;

    }

    
}
