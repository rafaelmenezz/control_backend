package com.tcscontrol.control_backend.patrimonydepartment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.patrimonydepartment.model.entity.PatrimonyDepartment;

public interface PatrimonyDepartmentRepository extends JpaRepository<PatrimonyDepartment, Long> {
      
}
