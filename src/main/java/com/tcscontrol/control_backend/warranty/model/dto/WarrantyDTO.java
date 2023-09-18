package com.tcscontrol.control_backend.warranty.model.dto;


public record WarrantyDTO(
      Long id,
      String dsGarantia,
      String dtValidade,
      String tipoGarantia
) {}
