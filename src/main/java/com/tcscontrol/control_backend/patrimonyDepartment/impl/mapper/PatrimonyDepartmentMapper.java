package com.tcscontrol.control_backend.patrimonyDepartment.impl.mapper;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.department.impl.mapper.DepartmentMapper;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.patrimonyDepartment.model.dto.PatrimonyDepartmentDTO;
import com.tcscontrol.control_backend.patrimonyDepartment.model.entity.PatrimonyDepartment;
import com.tcscontrol.control_backend.utilitarios.UtilData;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PatrimonyDepartmentMapper {
      
      private DepartmentMapper departmentMapper;
      private PatrimonyMapper patrimonyMapper;

      public PatrimonyDepartmentDTO toDto(PatrimonyDepartment allocation){
            if (allocation == null) {
                  return null;
            }
            return new PatrimonyDepartmentDTO(
                  allocation.getId(),
                  toDto(allocation.getParent()),
                  UtilData.toString(allocation.getDtAlocacao(), UtilData.FORMATO_DDMMAA),
                  UtilData.toString(allocation.getDtDevolucao(), UtilData.FORMATO_DDMMAA),
                  allocation.getNmObservacao(),
                  patrimonyMapper.toDto(allocation.getPatrimonio()),
                  departmentMapper.toDTO(allocation.getDepartamento()));
      }

      public PatrimonyDepartment toEntity(PatrimonyDepartmentDTO allocationDTO){
            if (allocationDTO == null) {
                  return null;
            }
            PatrimonyDepartment allocation = new PatrimonyDepartment();
            if (allocationDTO.id() != null) {
                  return null;
            }

            PatrimonyDepartment allocationParent = toEntity(allocationDTO.parent());
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
