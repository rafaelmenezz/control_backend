package com.tcscontrol.control_backend.allocation.impl.mapper;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.model.dto.AllocationDTO;
import com.tcscontrol.control_backend.allocation.model.dto.AllocationResponse;
import com.tcscontrol.control_backend.allocation.model.entity.Allocation;
import com.tcscontrol.control_backend.allocation_patrimony.model.dto.AllocationPatrimonyDTO;
import com.tcscontrol.control_backend.allocation_patrimony.model.entity.AllocationPatrimony;
import com.tcscontrol.control_backend.department.impl.mapper.DepartmentMapper;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.utilitarios.UtilData;


import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AllocationMapper {
      
      private DepartmentMapper departmentMapper;
      private PatrimonyMapper patrimonyMapper;

      public AllocationResponse toDto(Allocation allocation){
            if (allocation == null) {
                  return null;
            }

            List<AllocationPatrimonyDTO> patrimonies = allocation.getPatrimonios().stream().map(p->  this.toDTO(p)).collect(Collectors.toList());

            return new AllocationResponse(
                  allocation.getId(),
                  departmentMapper.toDTO(allocation.getDepartamento()),
                  patrimonies);
      }

      public Allocation toEntity(AllocationDTO allocationDTO){
            if (allocationDTO == null) {
                  return null;
            }
            Allocation allocation = new Allocation();
            if (allocationDTO.id() != null) {
                  return null;
            }
            Department department = departmentMapper.toEntity(allocationDTO.departament()); 
            allocation.setDepartamento(department);

            return allocation;

      }

      private AllocationPatrimonyDTO toDTO(AllocationPatrimony allocationPatrimony){

            if(allocationPatrimony == null){
                  return null;
            }
            return new AllocationPatrimonyDTO(
                  allocationPatrimony.getId(), 
                  UtilData.toString(allocationPatrimony.getDtAlocacao(), UtilData.FORMATO_DDMMAA), 
                  UtilData.toString(allocationPatrimony.getDtDevolucao(), UtilData.FORMATO_DDMMAA), 
                  allocationPatrimony.getNmObservacao(), 
                  patrimonyMapper.toDto(allocationPatrimony.getPatrimony()));
      }


}
