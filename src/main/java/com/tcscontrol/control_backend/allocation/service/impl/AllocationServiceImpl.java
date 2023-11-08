package com.tcscontrol.control_backend.allocation.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.dto.AllocationDTO;
import com.tcscontrol.control_backend.allocation.dto.AllocationResponse;
import com.tcscontrol.control_backend.allocation.mapper.AllocationMapper;
import com.tcscontrol.control_backend.allocation.model.Allocation;
import com.tcscontrol.control_backend.allocation.repository.AllocationRepository;
import com.tcscontrol.control_backend.allocation.service.AllocationService;
import com.tcscontrol.control_backend.allocationPatrimony.model.AllocationPatrimony;
import com.tcscontrol.control_backend.allocationPatrimony.repository.AllocationPatrimonyRepository;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AllocationServiceImpl implements AllocationService {

      private final AllocationRepository allocationRepository;
      private final AllocationMapper allocationMapper;
      private final AllocationPatrimonyRepository allocationPatrimonyRepository;
      private final PatrimonyMapper patrimonyMapper;

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
                        .orElseThrow(() -> new RecordNotFoundException("Allocation not found with id: " + id));
      }

      @Transactional
      @Override
      public AllocationResponse create(AllocationDTO allocationDTO) {
            if (allocationDTO == null) {
                  throw new IllegalArgumentException("AllocationDTO cannot be null");
            }

            Allocation allocation = allocationMapper.toEntity(allocationDTO);
            allocationRepository.save(allocation);

            if (allocationDTO.patrimonies() != null) {
                  allocationDTO.patrimonies().stream()
                              .map(patrimonyMapper::toEntity)
                              .map(patrimony -> {
                                    AllocationPatrimony allocationPatrimony = new AllocationPatrimony();
                                    allocationPatrimony.setAllocation(allocation);
                                    allocationPatrimony.setPatrimony(patrimony);
                                    return allocationPatrimonyRepository.save(allocationPatrimony);
                              })
                              .collect(Collectors.toList());
            }

            return allocationMapper.toDto(allocation);
      }
}