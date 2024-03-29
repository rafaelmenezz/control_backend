package com.tcscontrol.control_backend.maintenance;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tcscontrol.control_backend.maintenance.model.dto.MaintenanceDTO;


@Service
public interface MaintenanceService {

      List<MaintenanceDTO> list();

      MaintenanceDTO findById(Long id);

      MaintenanceDTO create(MaintenanceDTO maintenanceDTO);

      MaintenanceDTO update(Long id, MaintenanceDTO maintenanceDTO);

      MaintenanceDTO toSchedule(Long id,  MaintenanceDTO maintenanceDTO);

      MaintenanceDTO toExecute(Long id,  MaintenanceDTO maintenanceDTO);

      MaintenanceDTO toFinish(Long id,  MaintenanceDTO maintenanceDTO);

      void cancel(Long id,  MaintenanceDTO maintenanceDTO);
      
}
