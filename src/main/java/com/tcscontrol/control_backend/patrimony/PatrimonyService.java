package com.tcscontrol.control_backend.patrimony;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import com.tcscontrol.control_backend.patrymony.model.PatrimonyDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface PatrimonyService {
   
	public List<PatrimonyDTO> list();
	
	public PatrimonyDTO findById(@PathVariable @NotNull @Positive Long id);
      
	PatrimonyDTO criarPatrimonio(PatrimonyDTO patrimonioDTO);

    public PatrimonyDTO update(Long id, @Valid PatrimonyDTO patrimony);

    public void delete(@PathVariable @NotNull @Positive Long id);


      
}
