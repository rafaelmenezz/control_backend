package com.tcscontrol.control_backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcscontrol.control_backend.inventory.InventoryService;
import com.tcscontrol.control_backend.inventory.model.dto.InventoryDTO;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/inventory")
public class InventoryController {
    
    private final InventoryService inventoryService;

    @GetMapping
    public List<InventoryDTO> list() {
        return inventoryService.list();
    }

    @GetMapping("/{id}")
    public InventoryDTO findById(@PathVariable Long id) {
        return inventoryService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public InventoryDTO create(@RequestBody @Valid InventoryDTO inventoryDTO) {
        return inventoryService.create(inventoryDTO);
    }

    @PatchMapping("/{id}")
    public InventoryDTO update(@PathVariable Long id, @RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.update(id, inventoryDTO);
    }


}
