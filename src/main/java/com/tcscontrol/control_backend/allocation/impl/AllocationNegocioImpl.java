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
import com.tcscontrol.control_backend.exception.RecordNotFoundException;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AllocationNegocioImpl implements AllocationNegocio {

      private AllocationRepository allocationRepository;
      private AllocationMapper allocationMapper;

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
                        .orElseThrow(() -> new RecordNotFoundException(id));
      }

      @Override
      public AllocationResponse create(AllocationDTO allocationDTO) {
            Allocation allocation = allocationMapper.toEntity(allocationDTO);
            allocation = salvaAlocacao(allocation);
            return allocationMapper.toDto(allocation);
      }

      @Override
      public Allocation obtemLocalizacaoPatrimonio(Long id) {

            return null;

            // AllocationPatrimony ap =
            // allocationPatrimonyRepository.findByPatrimonyIdAndDtDevolucaoIsNull(id);

            // if (!UtilObjeto.isEmpty(ap) &&
            // allocationRepository.findById(ap.getAllocation().getId()).isPresent()) {
            // return allocationRepository.findById(ap.getAllocation().getId()).get();
            // } else {
            // return new Allocation();
            // }

      }

      private Allocation salvaAlocacao(Allocation allocation) {

            

            return allocationRepository.save(allocation);
      }

      @Override
      public Allocation salvaAllocation(Allocation allocation) {
            return salvaAlocacao(allocation);
      }

      @Override
      public Allocation pesquisaAllocationPorId(Long id) {
            return allocationRepository.findById(id)
                        .map(c -> c)
                        .orElseThrow(() -> new RecordNotFoundException(id));
      }

}
