package com.tcscontrol.control_backend.allocation.impl.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.model.dto.AllocationDTO;
import com.tcscontrol.control_backend.allocation.model.entity.Allocation;
import com.tcscontrol.control_backend.department.impl.mapper.DepartmentMapper;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.utilitarios.UtilData;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AllocationMapper {
      
      private DepartmentMapper departmentMapper;
      private PatrimonyMapper patrimonyMapper;

      public AllocationDTO toDto(Allocation allocation){
            if (allocation == null) {
                  return null;
            }

            List<PatrimonyDTO> patrimonyResponses = allocation.getPatrimonios()
                  .stream()
                  .map(patrimonyMapper::toDto).collect(Collectors.toList());

            return new AllocationDTO(
                  allocation.getId(),
                  UtilData.toString(allocation.getDtAlocacao(), UtilData.FORMATO_DDMMAA),
                  allocation.getNmObservacao(),
                  patrimonyResponses,
                  departmentMapper.toDTO(allocation.getDepartamento()));
      }

      public Allocation toEntity(AllocationDTO allocationDTO){
            if (allocationDTO == null) {
                  return null;
            }
            Allocation allocation = new Allocation();
            if (allocationDTO.id() != null) {
                  return null;
            }
            Set<Patrimony> patrimonys =  new HashSet<>(allocationDTO.patrimonies()
                  .stream()
                  .map(patrimonyMapper::toEntity).collect(Collectors.toList()));

            Department department = departmentMapper.toEntity(allocationDTO.departament()); 

            allocation.setDtAlocacao(UtilData.toDate(allocationDTO.dtAlocacao(), UtilData.FORMATO_DDMMAA));
            allocation.setNmObservacao(allocationDTO.observation());
            allocation.setPatrimonios(patrimonys);
            allocation.setDepartamento(department);

            return allocation;

      }

}
