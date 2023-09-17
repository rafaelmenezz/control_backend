package com.tcscontrol.control_backend.patrimony.impl.mapper;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.pessoa.fornecedor.Fornecedor;
import com.tcscontrol.control_backend.pessoa.fornecedor.FornecedorDTO;
import com.tcscontrol.control_backend.pessoa.fornecedor.FornecedorMapper;
import com.tcscontrol.control_backend.utilitarios.UtilControl;
import com.tcscontrol.control_backend.utilitarios.UtilData;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PatrimonyMapper {

    FornecedorMapper fornecedorMapper;
    
    public PatrimonyDTO toDto(Patrimony patrimony){
        if(patrimony == null){
            return null;
        }

        FornecedorDTO fornecedorDTO = fornecedorMapper.toDTO(patrimony.getFornecedor());

        return new PatrimonyDTO(patrimony.getId(),
         patrimony.getNrSerie(), 
         patrimony.getNmPatrimonio(), 
         patrimony.getNmDescricao(), 
         patrimony.getNrNotaFiscal(), 
         UtilData.toString( patrimony.getDtNotaFiscal(), UtilData.FORMATO_DDMMAA), 
         UtilData.toString( patrimony.getDtAquisicao(), UtilData.FORMATO_DDMMAA), 
         patrimony.getVlAquisicao(), 
         patrimony.getFixo(),
         patrimony.getTpStatus().getValue(), 
         fornecedorDTO);

    }

    public Patrimony toEntity(PatrimonyDTO patrimonyDTO){

        if (patrimonyDTO == null) {
            return null;
        }
        Patrimony patrimony = new Patrimony();
        if (patrimonyDTO.id() != null) {
            patrimony.setId(patrimonyDTO.id());
        }

        Fornecedor fornecedor = fornecedorMapper.toEntity(patrimonyDTO.fornecedor());

        patrimony.setNrSerie(patrimonyDTO.nrSerie());
        patrimony.setNmPatrimonio(patrimonyDTO.nmPatrimonio());
        patrimony.setNmDescricao(patrimonyDTO.nmDescricao());
        patrimony.setNrNotaFiscal(patrimonyDTO.nrNotaFiscal());
        patrimony.setDtNotaFiscal(UtilData.toDate(patrimonyDTO.dtNotaFiscal(), UtilData.FORMATO_DDMMAA));
        patrimony.setDtAquisicao(UtilData.toDate(patrimonyDTO.dtAquisicao(), UtilData.FORMATO_DDMMAA));
        patrimony.setVlAquisicao(patrimonyDTO.vlAquisicao());
        patrimony.setFixo(patrimonyDTO.fixo());
        patrimony.setTpStatus(UtilControl.convertStatusValue(patrimonyDTO.status()));
        patrimony.setFornecedor(fornecedor);

        return patrimony;

    }
}
