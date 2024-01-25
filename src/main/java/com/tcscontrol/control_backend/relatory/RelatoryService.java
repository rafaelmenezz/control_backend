package com.tcscontrol.control_backend.relatory;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.tcscontrol.control_backend.relatory.model.RelatorioRequestDTO;
import com.tcscontrol.control_backend.relatory.model.RelatoryResponseDTO;

@Service
public interface RelatoryService {
    
    void init();

    RelatoryResponseDTO store(RelatorioRequestDTO relatorioRequestDTO) throws IOException;

    Resource loadAsResource(RelatorioRequestDTO relatorioRequestDTO);
}
