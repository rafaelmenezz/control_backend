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
import com.tcscontrol.control_backend.department.DepartmentNegocio;
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
      private AllocationPatrimonyRepository allocationPatrimonyRepository;
      private DepartmentNegocio departmentNegocio;
      
      
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

                  Allocation allocation = allocationMapper.toEntity(allocationDTO);
                  allocation = salvaAlocacao(allocation);

                  return allocationMapper.toDto(allocation);      
            }else{
                  throw new IllegalRequestException(MSG_ALERTA_PATRIMONIO_INDISPONIVEL);
            }            
      }

      @Override
      public AllocationResponse update(Long id, AllocationDTO allocationDTO) {
           return allocationRepository.findById(id)
           .map(recordFound -> {
            Allocation allocation = allocationMapper.toEntity(allocationDTO);
            Department department = departmentNegocio.toEntity(allocationDTO.departament()); 

            recordFound.getPatrimonios();
            recordFound.getPatrimonios().clear();
            allocation.getPatrimonios().forEach(recordFound.getPatrimonios()::add);
            recordFound.setDepartamento(department);
            return allocationMapper.toDto(allocationRepository.save(recordFound));
           }).orElseThrow(()-> new RecordNotFoundException(id));
      }

      @Override
      public Allocation obtemLocalizacaoPatrimonio(Long id) {
            AllocationPatrimony ap = allocationPatrimonyRepository.findByPatrimonyIdAndDtDevolucaoIsNull(id); 

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
      
      private Allocation salvaAlocacao(Allocation allocation){
            return allocationRepository.save(allocation);
      }

      @Override
      public Allocation salvaAllocation(Allocation allocation) {
            return salvaAlocacao(allocation);
      }

      @Override
      public Allocation pesquisaAllocationPorId(Long id){
            return allocationRepository.findById(id)
            .map(c-> c)
            .orElseThrow(()-> new RecordNotFoundException(id));
      }

}
