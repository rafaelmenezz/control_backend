package com.tcscontrol.control_backend.department.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.tcscontrol.control_backend.department.impl.mapper.DepartmentMapper;
import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;
import com.tcscontrol.control_backend.department.model.repository.DepartmentRepository;
import com.tcscontrol.control_backend.department.service.DepartmentNegocio;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DepartmentImpl implements DepartmentNegocio{

    private DepartmentRepository departmentRepository;
    private DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentDTO> list() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO findById(@NotNull Long id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Override
    public DepartmentDTO create(@Valid @NotNull DepartmentDTO departmentDTO) {
        return departmentMapper.toDTO(departmentRepository.save(departmentMapper.toEntity(departmentDTO)));
    }

    @Override
    public DepartmentDTO update(Long id, @Valid @NotNull DepartmentDTO department) {
        return departmentRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setNmDepartamento(department.nmDepartamento());
                    recordFound.getUser().setId(department.id_usuario());
                    return departmentMapper.toDTO(departmentRepository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(id));        
    }

    @Override
    public void delete(@NotNull @Positive Long id) {
        departmentRepository
                .delete(departmentRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
    }

   
    
}
