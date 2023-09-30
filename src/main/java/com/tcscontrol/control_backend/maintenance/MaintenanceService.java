package com.tcscontrol.control_backend.maintenance;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcscontrol.control_backend.maintenance.model.dto.MaintenanceDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public interface MaintenanceService {

      List<MaintenanceDTO> list();

      MaintenanceDTO findById(Long id);

      MaintenanceDTO create(MaintenanceDTO maintenanceDTO);

      MaintenanceDTO update(Long id, MaintenanceDTO maintenanceDTO);

      public void delete(@PathVariable @NotNull @Positive Long id);
      
}
