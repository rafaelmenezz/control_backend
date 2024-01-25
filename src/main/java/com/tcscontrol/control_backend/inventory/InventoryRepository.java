package com.tcscontrol.control_backend.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.inventory.model.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    
}
