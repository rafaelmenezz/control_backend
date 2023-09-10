package com.tcscontrol.control_backend.inventory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.inventory.model.dto.InventoryDTO;

@Component
public interface InventoryService {
    
    public List<InventoryDTO> list();

    public InventoryDTO findById(Long id);

    public InventoryDTO create(InventoryDTO inventoryDTO);

    public InventoryDTO update(Long id, InventoryDTO inventoryDTO);
}
