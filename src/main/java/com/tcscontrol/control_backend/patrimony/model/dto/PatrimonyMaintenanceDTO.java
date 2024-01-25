package com.tcscontrol.control_backend.patrimony.model.dto;

public record PatrimonyMaintenanceDTO(
    String reason,
    String type,
    String situation,
    String dtStart,
    String dtEnd
) {}
