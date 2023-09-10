package com.tcscontrol.control_backend.allocation.impl.mapper;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.model.dto.AllocationDTO;
import com.tcscontrol.control_backend.allocation.model.entity.Allocation;
import com.tcscontrol.control_backend.department.impl.mapper.DepartmentMapper;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
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
            return new AllocationDTO(
                  allocation.getId(),
                  toDto(allocation.getParent()),
                  UtilData.toString(allocation.getDtAlocacao(), UtilData.FORMATO_DDMMAA),
                  UtilData.toString(allocation.getDtDevolucao(), UtilData.FORMATO_DDMMAA),
                  allocation.getNmObservacao(),
                  patrimonyMapper.toDto(allocation.getPatrimonio()),
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

            Allocation allocationParent = toEntity(allocationDTO.parent());
            Patrimony patrimony = patrimonyMapper.toEntity(allocationDTO.patrimonio());
            Department department = departmentMapper.toEntity(allocationDTO.departamento()); 

            allocation.setParent(allocationParent);
            allocation.setDtAlocacao(UtilData.toDate(allocationDTO.dtAlocacao(), UtilData.FORMATO_DDMMAA));
            allocation.setDtDevolucao(UtilData.toDate(allocationDTO.dtDevolucao(), UtilData.FORMATO_DDMMAA));
            allocation.setNmObservacao(allocationDTO.nmObservacao());
            allocation.setPatrimonio(patrimony);
            allocation.setDepartamento(department);

            return allocation;

      }


}
