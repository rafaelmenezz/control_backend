package com.tcscontrol.control_backend.allocation.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.AllocationNegocio;
import com.tcscontrol.control_backend.allocation.AllocationRepository;
import com.tcscontrol.control_backend.allocation.impl.mapper.AllocationMapper;
import com.tcscontrol.control_backend.allocation.model.dto.AllocationDTO;
import com.tcscontrol.control_backend.allocation.model.entity.Allocation;
import com.tcscontrol.control_backend.department.impl.mapper.DepartmentMapper;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.utilitarios.UtilData;

import lombok.AllArgsConstructor;


@Component
@AllArgsConstructor
public class AllocationNegocioImpl implements AllocationNegocio{
      
      private AllocationRepository allocationRepository;
      private AllocationMapper allocationMapper;
      private PatrimonyMapper patrimonyMapper;
      private DepartmentMapper departmentMapper;
      
      
      @Override
      public List<AllocationDTO> list() {
            return allocationRepository.findAll()
            .stream()
            .map(allocationMapper::toDto)
            .collect(Collectors.toList());
      }

      @Override
      public AllocationDTO findById(Long id) {
            return allocationRepository.findById(id)
            .map(allocationMapper::toDto)
            .orElseThrow(()-> new RecordNotFoundException(id));
      }

      @Override
      public AllocationDTO create(AllocationDTO allocationDTO) {
            return allocationMapper.toDto(allocationRepository.save(allocationMapper.toEntity(allocationDTO)));
      }

      @Override
      public AllocationDTO update(Long id, AllocationDTO allocationDTO) {
           return allocationRepository.findById(id)
           .map(recordFound -> {
            Allocation allocationParent = allocationMapper.toEntity(allocationDTO.parent());
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
