package com.tcscontrol.control_backend.allocation.impl.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.model.dto.AllocationDTO;
import com.tcscontrol.control_backend.allocation.model.dto.AllocationResponse;
import com.tcscontrol.control_backend.allocation.model.entity.Allocation;
import com.tcscontrol.control_backend.allocationPatrimony.model.entity.AllocationPatrimony;
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

      public AllocationResponse toDto(Allocation allocation) {
            if (allocation == null) {
                  return null;
            }

            List<PatrimonyDTO> patrimonies = new ArrayList<PatrimonyDTO>();

            for (AllocationPatrimony patrimony : allocation.getAllocationPatrimonies()) {
                  patrimonies.add(patrimonyMapper.toDto(patrimony.getPatrimony()));
            }

            return new AllocationResponse(
                        allocation.getId(),
                        departmentMapper.toDTO(allocation.getDepartamento()),
                        patrimonies);
      }

      public Allocation toEntity(AllocationDTO allocationDTO) {
            if (allocationDTO == null) {
                  return null;
            }
            Allocation allocation = new Allocation();
            if (allocationDTO.id() != null) {
                  return null;
            }
            Department department = departmentMapper.toEntity(allocationDTO.departament());
            allocation.setDepartamento(department);
            allocation.setDtAlocacao(UtilData.toDate(allocationDTO.dtAlocacao(), UtilData.FORMATO_DDMMAA));
            allocation.setNmObservacao(allocationDTO.observation());

            List<Patrimony> patrimonies = new ArrayList<Patrimony>();
            for (PatrimonyDTO patrimonyDTO : allocationDTO.patrimonies()) {
                  patrimonies.add(patrimonyMapper.toEntity(patrimonyDTO));
            }

            List<AllocationPatrimony> allocationPatrimonies = new ArrayList<AllocationPatrimony>();
            for (Patrimony patrimony : patrimonies) {
                  AllocationPatrimony allocationPatrimony = new AllocationPatrimony();
                  allocationPatrimony.setAllocation(allocation);
                  allocationPatrimony.setPatrimony(patrimony);
                  allocationPatrimonies.add(allocationPatrimony);
            }

            return allocation;

      }

}
