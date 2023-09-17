package com.tcscontrol.control_backend.warranty.model.dto;

import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;

public record WarrantyDTO(
      Long id,
      String dsGarantia,
      String dtValidade,
      String tipoGarantia,
      PatrimonyDTO patrimonio
) {}
