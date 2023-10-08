package com.tcscontrol.control_backend.patrimony;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.model.entity.Allocation;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;

@Component
public interface PatrimonyNegocio extends PatrimonyService {

    List<Patrimony> obtemPatrimonies(Long[] ids);

    Allocation actualAlocation(Long id);

    PatrimonyDTO toDTO(Patrimony patrimony);
    Patrimony toEntity(PatrimonyDTO patrimonyDTO);

    
}
