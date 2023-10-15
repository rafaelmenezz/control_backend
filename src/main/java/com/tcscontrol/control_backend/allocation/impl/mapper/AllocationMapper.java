package com.tcscontrol.control_backend.allocation.impl.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.warranty.model.dto.WarrantyDTO;
import com.tcscontrol.control_backend.warranty.model.entity.Warranty;

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

      private List<WarrantyDTO> listWarrantyDTOs(List<Warranty> list){
            return list.stream()
            .map(warranty -> new WarrantyDTO(
                    warranty.getId(),
                    warranty.getDsGarantia(),
                    UtilData.toString(warranty.getDtValidade(), UtilData.FORMATO_DDMMAA),
                    warranty.getTypewWarranty().getValue()))
            .collect(Collectors.toList());
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

      private AllocationPatrimony toEntity(AllocationPatrimonyDTO allocationPatrimonyDTO){

            if(allocationPatrimonyDTO == null){
                  return null;
            }
            AllocationPatrimony aP = new AllocationPatrimony();
            if(allocationPatrimonyDTO.id() != null){
                  aP.setId(allocationPatrimonyDTO.id());
            }

            aP.setDtAlocacao(UtilData.toDate(allocationPatrimonyDTO.dtAlocacao(), UtilData.FORMATO_DDMMAA));
            aP.setDtAlocacao(UtilData.toDate(allocationPatrimonyDTO.dtDevolucao(), UtilData.FORMATO_DDMMAA));
            aP.setNmObservacao(allocationPatrimonyDTO.nmObservacao());
            aP.setPatrimony(patrimonyMapper.toEntity(allocationPatrimonyDTO.patrimonio()));

            return aP;
  
      }

}
