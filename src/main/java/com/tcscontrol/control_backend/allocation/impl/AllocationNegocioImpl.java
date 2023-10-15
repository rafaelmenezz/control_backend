package com.tcscontrol.control_backend.allocation.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.AllocationNegocio;
import com.tcscontrol.control_backend.allocation.AllocationRepository;
import com.tcscontrol.control_backend.allocation.impl.mapper.AllocationMapper;
import com.tcscontrol.control_backend.allocation.model.dto.AllocationDTO;
import com.tcscontrol.control_backend.allocation.model.dto.AllocationResponse;
import com.tcscontrol.control_backend.allocation.model.entity.Allocation;
import com.tcscontrol.control_backend.allocation_patrimony.AllocationPatrimonyRepository;
import com.tcscontrol.control_backend.allocation_patrimony.model.entity.AllocationPatrimony;
import com.tcscontrol.control_backend.department.impl.mapper.DepartmentMapper;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.exception.IllegalRequestException;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;


import lombok.AllArgsConstructor;


@Component
@AllArgsConstructor
public class AllocationNegocioImpl implements AllocationNegocio{
      
      private AllocationRepository allocationRepository;
      private AllocationMapper allocationMapper;
      private DepartmentMapper departmentMapper;
      private AllocationPatrimonyRepository allocationPatrimonyRepository;
      
      
      @Override
      public List<AllocationResponse> list() {
            return allocationRepository.findAll()
            .stream()
            .map(allocationMapper::toDto)
            .collect(Collectors.toList());
      }

      @Override
      public AllocationResponse findById(Long id) {
            return allocationRepository.findById(id)
            .map(allocationMapper::toDto)
            .orElseThrow(()-> new RecordNotFoundException(id));
      }

      @Override
      public AllocationResponse create(AllocationDTO allocationDTO) {
            if (isListaDisponivelParaAlocacao(allocationDTO.patrimonies())) {
                  return allocationMapper.toDto(salvaAlocacao(allocationMapper.toEntity(allocationDTO)));      
            }else{
                  throw new IllegalRequestException("Patrimonios não disponivel para alocação!");
            }            
      }

      @Override
      public AllocationResponse update(Long id, AllocationDTO allocationDTO) {
           return allocationRepository.findById(id)
           .map(recordFound -> {
            Allocation allocation = allocationMapper.toEntity(allocationDTO);
            Department department = departmentMapper.toEntity(allocationDTO.departament()); 

            recordFound.getPatrimonios();
            recordFound.getPatrimonios().clear();
            allocation.getPatrimonios().forEach(recordFound.getPatrimonios()::add);
            recordFound.setDepartamento(department);
            return allocationMapper.toDto(allocationRepository.save(recordFound));
           }).orElseThrow(()-> new RecordNotFoundException(id));
      }

      @Override
      public Allocation obtemLocalizacaoPatrimonio(Long id) {
            AllocationPatrimony ap = pesquisAllocationPatrimonyPorId(id); 

            if (!UtilObjeto.isEmpty(ap) &&  allocationRepository.findById(ap.getAllocation().getId()).isPresent()) {
                  return allocationRepository.findById(ap.getAllocation().getId()).get();
            } else {
                  return new Allocation();      
            }
            
      }

      private Boolean isListaDisponivelParaAlocacao(List<PatrimonyDTO> pDTO){
            
           if (pDTO == null) {
            return false;
           }
            for (PatrimonyDTO p : pDTO) {
                  Allocation a = obtemLocalizacaoPatrimonio(p.id());
                  if (!UtilObjeto.isEmpty(a.getDepartamento())) {
                       return false; 
                  }
           }
      return true;
      } 
      
      private AllocationPatrimony pesquisAllocationPatrimonyPorId(Long id){
            return allocationPatrimonyRepository.findByPatrimonyIdAndDtDevolucaoIsNull(id);
      }

      private Allocation salvaAlocacao(Allocation allocation){
            return allocationRepository.save(allocation);
      }

      @Override
      public Allocation salvaAllocation(Allocation allocation) {
            return salvaAlocacao(allocation);
      }
}
