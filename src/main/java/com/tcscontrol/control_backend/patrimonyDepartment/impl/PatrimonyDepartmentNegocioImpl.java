package com.tcscontrol.control_backend.patrimonyDepartment.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.department.impl.mapper.DepartmentMapper;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.patrimonyDepartment.PatrimonyDepartmentNegocio;
import com.tcscontrol.control_backend.patrimonyDepartment.PatrimonyDepartmentRepository;
import com.tcscontrol.control_backend.patrimonyDepartment.impl.mapper.PatrimonyDepartmentMapper;
import com.tcscontrol.control_backend.patrimonyDepartment.model.dto.PatrimonyDepartmentDTO;
import com.tcscontrol.control_backend.patrimonyDepartment.model.entity.PatrimonyDepartment;
import com.tcscontrol.control_backend.utilitarios.UtilData;

import lombok.AllArgsConstructor;


@Component
@AllArgsConstructor
public class PatrimonyDepartmentNegocioImpl implements PatrimonyDepartmentNegocio{
      
      private PatrimonyDepartmentRepository allocationRepository;
      private PatrimonyDepartmentMapper allocationMapper;
      private PatrimonyMapper patrimonyMapper;
      private DepartmentMapper departmentMapper;
      
      
      @Override
      public List<PatrimonyDepartmentDTO> list() {
            return allocationRepository.findAll()
            .stream()
            .map(allocationMapper::toDto)
            .collect(Collectors.toList());
      }

      @Override
      public PatrimonyDepartmentDTO findById(Long id) {
            return allocationRepository.findById(id)
            .map(allocationMapper::toDto)
            .orElseThrow(()-> new RecordNotFoundException(id));
      }

      @Override
      public PatrimonyDepartmentDTO create(PatrimonyDepartmentDTO allocationDTO) {
            return allocationMapper.toDto(allocationRepository.save(allocationMapper.toEntity(allocationDTO)));
      }

      @Override
      public PatrimonyDepartmentDTO update(Long id, PatrimonyDepartmentDTO allocationDTO) {
           return allocationRepository.findById(id)
           .map(recordFound -> {
            PatrimonyDepartment allocationParent = allocationMapper.toEntity(allocationDTO.parent());
            Patrimony patrimony = patrimonyMapper.toEntity(allocationDTO.patrimonio());
            Department department = departmentMapper.toEntity(allocationDTO.departamento()); 

            recordFound.setParent(allocationParent);
            recordFound.setDtAlocacao(UtilData.toDate(allocationDTO.dtAlocacao(), UtilData.FORMATO_DDMMAA));
            recordFound.setDtDevolucao(UtilData.toDate(allocationDTO.dtDevolucao(), UtilData.FORMATO_DDMMAA));
            recordFound.setNmObservacao(allocationDTO.nmObservacao());
            recordFound.setPatrimonio(patrimony);
            recordFound.setDepartamento(department);

            return allocationMapper.toDto(allocationRepository.save(recordFound));
           }).orElseThrow(()-> new RecordNotFoundException(id));
      }
      
}
