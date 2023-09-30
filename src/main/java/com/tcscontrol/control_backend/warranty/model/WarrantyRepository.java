package com.tcscontrol.control_backend.warranty.model;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.warranty.model.entity.Warranty;

public interface WarrantyRepository extends JpaRepository<Warranty, Long>{
      
}
