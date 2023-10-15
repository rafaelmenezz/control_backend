package com.tcscontrol.control_backend.allocation_patrimony.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.model.dto.AllocationDTO;
import com.tcscontrol.control_backend.allocation.model.entity.Allocation;
import com.tcscontrol.control_backend.allocation_patrimony.model.entity.AllocationPatrimony;
import com.tcscontrol.control_backend.department.impl.mapper.DepartmentMapper;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.utilitarios.UtilData;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AllocationPatrimonyMapper {
    
    PatrimonyMapper patrimonyMapper;
    DepartmentMapper departmentMapper;

     public Allocation toEntity(AllocationDTO allocationDTO){
            if (allocationDTO == null) {
                  return null;
            }

            Allocation allocation = new Allocation();
            
            if (allocationDTO.id() != null) {
                  return null;
            }

            List<Patrimony> patrimonys =  allocationDTO.patrimonies()
                  .stream()
                  .map(patrimonyMapper::toEntity).collect(Collectors.toList());

            List<AllocationPatrimony> listItens = new ArrayList<>();

            for (Patrimony p : patrimonys) {
                  AllocationPatrimony aP = new AllocationPatrimony();
                  if (p.getId() != null) {
                     aP.setId(p.getId());  
                  }
                  aP.setAllocation(allocation);
                  aP.setDtAlocacao(UtilData.toDate(allocationDTO.dtAlocacao(), UtilData.FORMATO_DDMMAA));
                  aP.setNmObservacao(allocationDTO.observation());
                  aP.setPatrimony(p);

                  listItens.add(aP);
            }

            Department department = departmentMapper.toEntity(allocationDTO.departament()); 
            allocation.setDepartamento(department);
            allocation.getPatrimonios().clear();
            allocation.setPatrimonios(listItens);

            return allocation;

      }
}
