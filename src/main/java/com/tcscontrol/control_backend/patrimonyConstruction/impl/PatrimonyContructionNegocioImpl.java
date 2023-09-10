package com.tcscontrol.control_backend.patrimonyConstruction.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.department.impl.mapper.DepartmentMapper;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.patrimonyConstruction.PatrimonyContructionNegocio;
import com.tcscontrol.control_backend.patrimonyConstruction.PatrimonyContructionRepository;
import com.tcscontrol.control_backend.patrimonyConstruction.impl.mapper.PatrimonyContructionMapper;
import com.tcscontrol.control_backend.patrimonyConstruction.model.dto.PatrimonyContructionDTO;
import com.tcscontrol.control_backend.patrimonyConstruction.model.entity.PatrimonyContruction;
import com.tcscontrol.control_backend.utilitarios.UtilData;

import lombok.AllArgsConstructor;


@Component
@AllArgsConstructor
public class PatrimonyContructionNegocioImpl implements PatrimonyContructionNegocio{
      
      private PatrimonyContructionRepository allocationRepository;
      private PatrimonyContructionMapper allocationMapper;
      private PatrimonyMapper patrimonyMapper;
      private DepartmentMapper departmentMapper;
      
      
      @Override
      public List<PatrimonyContructionDTO> list() {
            return allocationRepository.findAll()
            .stream()
            .map(allocationMapper::toDto)
            .collect(Collectors.toList());
      }

      @Override
      public PatrimonyContructionDTO findById(Long id) {
            return allocationRepository.findById(id)
            .map(allocationMapper::toDto)
            .orElseThrow(()-> new RecordNotFoundException(id));
      }

      @Override
      public PatrimonyContructionDTO create(PatrimonyContructionDTO allocationDTO) {
            return allocationMapper.toDto(allocationRepository.save(allocationMapper.toEntity(allocationDTO)));
      }

      @Override
      public PatrimonyContructionDTO update(Long id, PatrimonyContructionDTO allocationDTO) {
           return allocationRepository.findById(id)
           .map(recordFound -> {
            PatrimonyContruction allocationParent = allocationMapper.toEntity(allocationDTO.parent());
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
