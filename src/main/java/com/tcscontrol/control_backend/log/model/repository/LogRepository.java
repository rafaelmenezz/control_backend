package com.tcscontrol.control_backend.log.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.log.model.entity.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

    Log findByNmNomeCampoAlterado(String nomeCampoAlterado);
    
}
