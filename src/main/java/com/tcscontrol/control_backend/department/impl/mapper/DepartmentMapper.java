package com.tcscontrol.control_backend.department.impl.mapper;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.pessoa.user.impl.mapper.UserMapper;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DepartmentMapper {

    private final UserMapper userMapper;

     public DepartmentDTO toDTO(Department department) {
        if (department == null) {
            return null;
        }

        return new DepartmentDTO(
                department.getId(),
                department.getNmDepartamento(),
                userMapper.toCreateDto(department.getUser()));
        
    }

    public Department toEntity(DepartmentDTO departmentDTO){
        if(departmentDTO == null){
            return null;
        }

        Department department = new Department();
        if(departmentDTO.id() != null){
            department.setId(departmentDTO.id());
        }

        User user = userMapper.toCreateEntity(departmentDTO.user());


        department.setId(departmentDTO.id());
        department.setNmDepartamento(departmentDTO.nmDepartamento());
        department.setUser(user);;

        return department;

    }
}
