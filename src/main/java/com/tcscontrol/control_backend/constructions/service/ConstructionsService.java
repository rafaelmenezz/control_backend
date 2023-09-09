package com.tcscontrol.control_backend.constructions.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcscontrol.control_backend.constructions.model.dto.ConstructionsDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public interface ConstructionsService {

    public List<ConstructionsDTO> list();

    public ConstructionsDTO findById(@PathVariable @NotNull Long id);

    public ConstructionsDTO create(@Valid ConstructionsDTO constructionsDTO);

    public ConstructionsDTO update(Long id, @Valid ConstructionsDTO constructions);

    public void delete(@PathVariable @NotNull @Positive Long id);
    
    
}
