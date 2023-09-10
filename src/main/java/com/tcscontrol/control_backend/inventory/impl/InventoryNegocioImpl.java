package com.tcscontrol.control_backend.inventory.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.inventory.InventoryNegocio;
import com.tcscontrol.control_backend.inventory.InventoryRepository;
import com.tcscontrol.control_backend.inventory.impl.mapper.InventoryMapper;
import com.tcscontrol.control_backend.inventory.model.dto.InventoryDTO;
import com.tcscontrol.control_backend.utilitarios.UtilData;

import lombok.AllArgsConstructor;

@Component(value = "inventoryNegocio")
@AllArgsConstructor
public class InventoryNegocioImpl implements InventoryNegocio {

    private InventoryRepository inventoryRepository;
    private InventoryMapper inventoryMapper;

    @Override
    public List<InventoryDTO> list() {
        return inventoryRepository.findAll()
        .stream()
        .map(inventoryMapper::toDto)
        .collect(Collectors.toList());
    }

    @Override
    public InventoryDTO findById(Long id) {
       return inventoryRepository.findById(id)
       .map(inventoryMapper::toDto)
       .orElseThrow(()-> new RecordNotFoundException(id));
    }

    @Override
    public InventoryDTO create(InventoryDTO inventoryDTO) {
        return inventoryMapper.toDto(inventoryRepository.save(inventoryMapper.toEntity(inventoryDTO)));
    }

    @Override
    public InventoryDTO update(Long id, InventoryDTO inventoryDTO) {
        return inventoryRepository.findById(id)
        .map(recordFound -> {
            recordFound.setDtAgendada(UtilData.toDate(inventoryDTO.dtAgendada(), UtilData.FORMATO_DDMMAA));
            recordFound.setDtRealizada(UtilData.toDate(inventoryDTO.dtRealizada(), UtilData.FORMATO_DDMMAA));
            return inventoryMapper.toDto(inventoryRepository.save(recordFound));
        }).orElseThrow(()-> new RecordNotFoundException(id));
    }
    
}
