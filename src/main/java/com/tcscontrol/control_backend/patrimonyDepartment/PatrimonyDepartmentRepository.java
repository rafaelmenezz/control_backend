package com.tcscontrol.control_backend.patrimonyDepartment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.patrimonyDepartment.model.entity.PatrimonyDepartment;

public interface PatrimonyDepartmentRepository extends JpaRepository<PatrimonyDepartment, Long> {
      
}
