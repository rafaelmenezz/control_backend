package com.tcscontrol.control_backend.request_patrimony;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.request_patrimony.model.entity.RequestPatrimony;

public interface RequestPatrimonyRepository extends JpaRepository<RequestPatrimony, Long>{
    
}
