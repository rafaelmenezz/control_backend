package com.tcscontrol.control_backend.requests;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.requests.model.entity.Requests;

public interface RequestsRepository extends JpaRepository<Requests, Long> {
      
}
