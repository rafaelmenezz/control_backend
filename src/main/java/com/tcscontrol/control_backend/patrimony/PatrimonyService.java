package com.tcscontrol.control_backend.patrimony;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyResponse;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public interface PatrimonyService {
 
    List<PatrimonyResponse> list();

    public PatrimonyResponse findById(Long id);

    public PatrimonyResponse create(PatrimonyDTO patrimonyDTO);

    public PatrimonyResponse update(Long id, PatrimonyDTO patrimonyDTO);

    public void delete(@PathVariable @NotNull @Positive Long id);

    public List<PatrimonyResponse> search(String nmPatrimonio, String nrSerie, String dsPatrimonio, String nrCpf, String nmFornecedor, String dtAquisicao);

    public List<PatrimonyResponse> listPatrimoniesFixOrNotFix(String nmPatrimony,Boolean fixo);

    public PatrimonyDTO createCasualytyPatrimonie(PatrimonyDTO patrimonyDTO);

}
