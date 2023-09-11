package com.tcscontrol.control_backend.log.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tcscontrol.control_backend.log.impl.mapper.LogMapper;
import com.tcscontrol.control_backend.log.model.dto.LogDTO;
import com.tcscontrol.control_backend.log.model.entity.Log;
import com.tcscontrol.control_backend.log.model.repository.LogRepository;
import com.tcscontrol.control_backend.log.service.LogNegocio;

import jakarta.transaction.Transactional;

@Service
public class LogImpl implements LogNegocio {

    private LogRepository logRepository;
    private LogMapper logMapper;

    public LogImpl(LogRepository logRepository, LogMapper logMapper) {
        this.logRepository = logRepository;
        this.logMapper = logMapper;
    }

    @Override
    public List<LogDTO> list() {
        List<Log> logs = logRepository.findAll();
        return logs.stream()
                .map(logMapper::logToLogDTO)
                .collect(Collectors.toList());
    }
    @Override
    public LogDTO findById(Long id) {
        Log log = logRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Log not found with id: " + id));
        return logMapper.logToLogDTO(log);
    }
    
    @Override
    @Transactional
    public LogDTO create(LogDTO logDTO) {
        Log log = logMapper.logDTOToLog(logDTO);
        Log savedLog = logRepository.save(log);
        return logMapper.logToLogDTO(savedLog);
        }
}
