package com.tcscontrol.control_backend.patrimony;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public interface PatrimonyService {
 
    List<PatrimonyDTO> list();

    public PatrimonyDTO findById(Long id);

    public PatrimonyDTO create(PatrimonyDTO patrimonyDTO);

    public PatrimonyDTO update(Long id, PatrimonyDTO patrimonyDTO);

    public void delete(@PathVariable @NotNull @Positive Long id);

    public List<PatrimonyDTO> search(String nmPatrimonio, String nrSerie, String dsPatrimonio, String nrCpf, String nmFornecedor, String dtAquisicao);
}
