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
import com.tcscontrol.control_backend.allocationPatrimony.AllocationPatrimonyRepository;
import com.tcscontrol.control_backend.allocationPatrimony.model.entity.AllocationPatrimony;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AllocationNegocioImpl implements AllocationNegocio {

      private AllocationRepository allocationRepository;
      private AllocationMapper allocationMapper;
      private AllocationPatrimonyRepository allocationPatrimonyRepository;
      private PatrimonyMapper patrimonyMapper;

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
            allocation = save(allocation);

            if (allocationDTO.patrimonies() != null) {
                  for (PatrimonyDTO patrimonyDTO : allocationDTO.patrimonies()) {
                        AllocationPatrimony allocationPatrimony = new AllocationPatrimony();
                        Patrimony patrimony = patrimonyMapper.toEntity(patrimonyDTO);
                        allocationPatrimony.setAllocation(allocation);
                        allocationPatrimony.setPatrimony(patrimony);

                        allocationPatrimonyRepository.save(allocationPatrimony);
                  }
            }

            return allocationMapper.toDto(allocation);
      }

      @Override
      public Allocation save(Allocation allocation) {
            return allocationRepository.save(allocation);
      }

}