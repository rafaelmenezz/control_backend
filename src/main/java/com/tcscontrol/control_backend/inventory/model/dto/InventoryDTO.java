package com.tcscontrol.control_backend.inventory.model.dto;

public record InventoryDTO(
    Long id, 
    String dtAgendada,
    String dtRealizada
) {}
