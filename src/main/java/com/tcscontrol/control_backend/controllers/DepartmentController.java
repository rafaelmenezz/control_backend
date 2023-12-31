package com.tcscontrol.control_backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcscontrol.control_backend.department.DepartmentService;
import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/departamento")
public class DepartmentController {
    
    private final DepartmentService departmentService;

    @GetMapping
    public List<DepartmentDTO> list() {
        return departmentService.list();
    }

    @GetMapping("/{id}")
    public DepartmentDTO findById(@PathVariable Long id) {
        return departmentService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public DepartmentDTO create(@RequestBody @Valid DepartmentDTO departmentDTO) {
        return departmentService.create(departmentDTO);
    }

    @PatchMapping("/{id}")
    public DepartmentDTO update(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO) {
        return departmentService.update(id, departmentDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        departmentService.delete(id);
    }

}
