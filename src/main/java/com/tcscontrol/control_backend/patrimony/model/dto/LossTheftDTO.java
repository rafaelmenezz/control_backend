package com.tcscontrol.control_backend.patrimony.model.dto;

public record LossTheftDTO(
    Long id,
    String observation,
    String dtLost
) {}
