package com.tcscontrol.control_backend.maintenance;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.maintenance.model.entity.Maintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
      
}
