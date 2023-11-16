package com.tcscontrol.control_backend.patrimony.model.dto;

public record PatrimonyConstructionDTO(
    String nmConstruction,
    String nmResponsible,
    String dtStart, 
    String dtEnd
) {}
