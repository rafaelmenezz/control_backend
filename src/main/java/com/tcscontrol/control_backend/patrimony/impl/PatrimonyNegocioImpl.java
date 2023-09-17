package com.tcscontrol.control_backend.patrimony.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.patrimony.PatrimonyNegocio;
import com.tcscontrol.control_backend.patrimony.PatrimonyRepository;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.pessoa.fornecedor.Fornecedor;
import com.tcscontrol.control_backend.pessoa.fornecedor.FornecedorMapper;
import com.tcscontrol.control_backend.utilitarios.UtilCast;
import com.tcscontrol.control_backend.utilitarios.UtilControl;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@Component(value = "patrimonyNegocio")
@AllArgsConstructor
public class PatrimonyNegocioImpl implements PatrimonyNegocio {

    private PatrimonyRepository patrimonyRepository;
    private PatrimonyMapper patrimonyMapper;
    private FornecedorMapper fornecedorMapper;

    @Override
    public List<PatrimonyDTO> list() {
        return patrimonyRepository.findAll()
                .stream()
                .map(patrimonyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PatrimonyDTO findById(Long id) {
        return patrimonyRepository.findById(id)
                .map(patrimonyMapper::toDto)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Override
    public PatrimonyDTO create(PatrimonyDTO patrimonyDTO) {
        return patrimonyMapper.toDto(patrimonyRepository.save(patrimonyMapper.toEntity(patrimonyDTO)));
    }

    @Override
    public PatrimonyDTO update(Long id, PatrimonyDTO patrimonyDTO) {
        return patrimonyRepository.findById(id)
                .map(recordFound -> {
                    Fornecedor fornecedor = fornecedorMapper.toEntity(patrimonyDTO.fornecedor());
                    recordFound.setNrSerie(patrimonyDTO.nrSerie());
                    recordFound.setNmPatrimonio(patrimonyDTO.nmPatrimonio());
                    recordFound.setNmDescricao(patrimonyDTO.nmDescricao());
                    recordFound.setNrNotaFiscal(patrimonyDTO.nrNotaFiscal());
                    recordFound.setDtNotaFiscal(UtilData.toDate(patrimonyDTO.dtNotaFiscal(), UtilData.FORMATO_DDMMAA));
                    recordFound.setDtAquisicao(UtilData.toDate(patrimonyDTO.dtAquisicao(), UtilData.FORMATO_DDMMAA));
                    recordFound.setVlAquisicao(patrimonyDTO.vlAquisicao());
                    recordFound.setFixo(patrimonyDTO.fixo());
                    recordFound.setTpStatus(UtilControl.convertStatusValue(patrimonyDTO.status()));
                    recordFound.setFornecedor(fornecedor);
                    return patrimonyMapper.toDto(patrimonyRepository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Override
    public void delete(@NotNull @Positive Long id) {
       patrimonyRepository.delete(patrimonyRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
    }

    @Override
    public List<PatrimonyDTO> search(String nmPatrimonio, String nrSerie, String dsPatrimonio, String nrCnpj, String nmFornecedor, String dtAquisicao){ 
                        
        Integer numeroSerie = null;
        if(!UtilObjeto.isEmpty(nrSerie)){
            numeroSerie = UtilCast.toInteger(nrSerie);
        }
        Date dt = null; 
        if (!UtilObjeto.isEmpty(dtAquisicao)) {
            dt = UtilData.toDate(dtAquisicao, UtilData.FORMATO_DDMMAA);
        }
        
        return patrimonyRepository.findByNmPatrimonioContainingOrNrSerieContainingOrNmDescricaoContainingOrFornecedorNrCnpjContainingOrFornecedorNmNameContainingOrDtAquisicaoContaining(nmPatrimonio, numeroSerie, dsPatrimonio, nrCnpj, nmFornecedor, dt)
                .stream()
                .map(patrimonyMapper::toDto)
                .collect(Collectors.toList());
    }

}
