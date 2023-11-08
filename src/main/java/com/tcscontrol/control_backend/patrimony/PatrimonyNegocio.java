package com.tcscontrol.control_backend.patrimony;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;

@Component
public interface PatrimonyNegocio extends PatrimonyService {

    List<Patrimony> obtemPatrimonies(Long[] ids);

    PatrimonyDTO toDTO(Patrimony patrimony);

    Patrimony toEntity(PatrimonyDTO patrimonyDTO);

    List<Patrimony> atulizaPatrimonios(List<Patrimony> patrimonies);

    List<Patrimony> toListEntity(List<PatrimonyDTO> patrimonyDTOs);

    Patrimony atualizaPatrimonio(Patrimony patrimony);

}
