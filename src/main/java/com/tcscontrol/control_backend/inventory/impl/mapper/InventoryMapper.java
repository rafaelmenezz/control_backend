package com.tcscontrol.control_backend.inventory.impl.mapper;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.inventory.model.dto.InventoryDTO;
import com.tcscontrol.control_backend.inventory.model.entity.Inventory;
import com.tcscontrol.control_backend.utilitarios.UtilData;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class InventoryMapper {
    

    public InventoryDTO toDto(Inventory inventory){
        if (inventory == null) {
            return null;
        }

        return new InventoryDTO(
        inventory.getId(),
        UtilData.toString(inventory.getDtAgendada(), UtilData.FORMATO_DDMMAA),
        UtilData.toString(inventory.getDtRealizada(), UtilData.FORMATO_DDMMAA)) ;
    }

    public Inventory toEntity(InventoryDTO inventoryDTO){
        if (inventoryDTO == null) {
            return null;
        }
        Inventory inventory = new Inventory();
        if (inventoryDTO.id() != null) {
            inventory.setId(inventoryDTO.id());
        }

        inventory.setDtAgendada(UtilData.toDate(inventoryDTO.dtAgendada(), UtilData.FORMATO_DDMMAA));
        inventory.setDtRealizada(UtilData.toDate(inventoryDTO.dtRealizada(), UtilData.FORMATO_DDMMAA));

        return inventory;
    }
}
