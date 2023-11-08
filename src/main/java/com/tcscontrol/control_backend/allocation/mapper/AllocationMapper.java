package com.tcscontrol.control_backend.allocation.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.dto.AllocationDTO;
import com.tcscontrol.control_backend.allocation.dto.AllocationResponse;
import com.tcscontrol.control_backend.allocation.model.Allocation;
import com.tcscontrol.control_backend.allocationPatrimony.model.AllocationPatrimony;
import com.tcscontrol.control_backend.department.impl.mapper.DepartmentMapper;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.utilitarios.UtilData;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AllocationMapper {

      private final DepartmentMapper departmentMapper;
      private final PatrimonyMapper patrimonyMapper;

      public AllocationResponse toDto(Allocation allocation) {
            if (allocation == null) {
                  throw new IllegalArgumentException("Allocation cannot be null");
            }

            List<PatrimonyDTO> patrimonies = allocation.getAllocationPatrimonies().stream()
                        .map(AllocationPatrimony::getPatrimony)
                        .map(patrimonyMapper::toDto)
                        .collect(Collectors.toList());

            return new AllocationResponse(
                        allocation.getId(),
                        UtilData.toString(allocation.getDtAlocacao(), UtilData.FORMATO_DDMMAA),
                        allocation.getNmObservacao(),
                        departmentMapper.toDTO(allocation.getDepartamento()),
                        patrimonies);
      }

      public Allocation toEntity(AllocationDTO allocationDTO) {
            if (allocationDTO == null) {
                  throw new IllegalArgumentException("AllocationDTO cannot be null");
            }
            if (allocationDTO.id() != null) {
                  throw new IllegalArgumentException("AllocationDTO id must be null when creating a new Allocation");
            }

            Department department = departmentMapper.toEntity(allocationDTO.departament());
            Allocation allocation = new Allocation();
            allocation.setDepartamento(department);
            allocation.setDtAlocacao(UtilData.toDate(allocationDTO.dtAlocacao(), UtilData.FORMATO_DDMMAA));
            allocation.setNmObservacao(allocationDTO.observation());
            allocation.setAllocationPatrimonies(new ArrayList<>());
            return allocation;
      }

}
