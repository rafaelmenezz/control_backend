package com.tcscontrol.control_backend.department.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.department.model.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

    Optional<Department> findByDepartamento(String departamento);
    
}
