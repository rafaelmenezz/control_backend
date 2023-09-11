package com.tcscontrol.control_backend.log.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tcscontrol.control_backend.log.model.dto.LogDTO;

@Service
public interface LogService {
    
    public List<LogDTO> list();

    public LogDTO findById(Long id);

    public LogDTO create(LogDTO logDTO);

    //public LogDTO update(Long id, LogDTO log);

    //public void delete(Long id);

}
