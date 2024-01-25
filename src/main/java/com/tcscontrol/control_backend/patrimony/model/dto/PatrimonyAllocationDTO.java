package com.tcscontrol.control_backend.patrimony.model.dto;

public record PatrimonyAllocationDTO(
    String nmDepartment,
    String nmResponsible,
    String dtStart,
    String dtEnd
) {}
