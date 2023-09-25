package com.tcscontrol.control_backend.patrimony.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.AllocationNegocio;
import com.tcscontrol.control_backend.allocation.model.entity.Allocation;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.patrimony.PatrimonyNegocio;
import com.tcscontrol.control_backend.patrimony.PatrimonyRepository;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyResponse;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.pessoa.fornecedor.Fornecedor;
import com.tcscontrol.control_backend.pessoa.fornecedor.FornecedorNegocio;
import com.tcscontrol.control_backend.utilitarios.UtilCast;
import com.tcscontrol.control_backend.utilitarios.UtilControl;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;
import com.tcscontrol.control_backend.warranty.model.entity.Warranty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@Component(value = "patrimonyNegocio")
@AllArgsConstructor
public class PatrimonyNegocioImpl implements PatrimonyNegocio {

    private PatrimonyRepository patrimonyRepository;
    private PatrimonyMapper patrimonyMapper;
    private FornecedorNegocio fornecedorNegocio;
    private AllocationNegocio allocationNegocio;

    @Override
    public List<PatrimonyResponse> list() {
        return patrimonyRepository.findAll()
                .stream()
                .map(patrimonyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PatrimonyResponse findById(Long id) {
        return patrimonyRepository.findById(id)
                .map(patrimonyMapper::toResponse)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Override
    public PatrimonyResponse create(PatrimonyDTO patrimonyDTO) {    
        return patrimonyMapper.toResponse(patrimonyRepository.save(patrimonyMapper.toEntity(patrimonyDTO)));
    }

    @Override
    public PatrimonyResponse update(Long id, PatrimonyDTO patrimonyDto) {
        return patrimonyRepository.findById(id)
                .map(recordFound -> {
                    Patrimony patrimony = patrimonyMapper.toEntity(patrimonyDto);
                    Fornecedor fornecedor = fornecedorNegocio.pesquisaFornecedorCnpj(patrimonyDto.nrCnpj());
                    if(fornecedor == null){
                        throw new IllegalArgumentException("Fonecedor não encontrado!");
                    }
                    List<Warranty> warranties = patrimonyDto.warranties()
                    .stream()
                    .map(warranty -> new Warranty(
                            warranty.id(), 
                            warranty.dsGarantia(), 
                            UtilData.toDate(warranty.dtValidade(), UtilData.FORMATO_DDMMAA), 
                            UtilControl.convertTypeWarrantyValue(warranty.tipoGarantia()), 
                            recordFound))
                    .collect(Collectors.toList());
                    recordFound.setNrSerie(patrimonyDto.nrSerie());
                    recordFound.setNmPatrimonio(patrimonyDto.nmDescricao());
                    recordFound.setNrNotaFiscal(patrimonyDto.nrNF());
                    recordFound.setDtNotaFiscal(UtilData.toDate(patrimonyDto.dtNF(), UtilData.FORMATO_DDMMAA));
                    recordFound.setDtAquisicao(UtilData.toDate(patrimonyDto.dtAquisicao(), UtilData.FORMATO_DDMMAA));
                    recordFound.setVlAquisicao(patrimonyDto.vlAquisicao());
                    recordFound.setFixo(patrimonyDto.fixo());
                    recordFound.setFornecedor(fornecedor);
                    recordFound.getWarrantys().clear();
                    patrimony.getWarrantys().forEach(recordFound.getWarrantys()::add);
                    recordFound.setWarrantys(warranties);
                    return patrimonyMapper.toResponse(patrimonyRepository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Override
    public void delete(@NotNull @Positive Long id) {
       patrimonyRepository.delete(patrimonyRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
    }

    @Override
    public List<PatrimonyResponse> search(String nmPatrimonio, String nrSerie, String dsPatrimonio, String nrCnpj, String nmFornecedor, String dtAquisicao){ 
                        
        Integer numeroSerie = null;
        if(!UtilObjeto.isEmpty(nrSerie)){
            numeroSerie = UtilCast.toInteger(nrSerie);
        }
        Date dt = null; 
        if (!UtilObjeto.isEmpty(dtAquisicao)) {
            dt = UtilData.toDate(dtAquisicao, UtilData.FORMATO_DDMMAA);
        }
        
        return patrimonyRepository.findByNmPatrimonioContainingOrNrSerieOrNmDescricaoContainingOrFornecedorNrCnpjContainingOrFornecedorNmNameContainingOrDtAquisicaoContaining(nmPatrimonio, numeroSerie, dsPatrimonio, nrCnpj, nmFornecedor, dt)
                .stream()
                .map(patrimonyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Patrimony> obtemPatrimonies(Long[] ids) {
    return patrimonyRepository.findAllById( Arrays.asList(ids));

    }

    @Override
    public Allocation actualAlocation(Long id) {
       return allocationNegocio.obtemLocalizacaoPatrimonio(id);
    }

    @Override
    public List<PatrimonyResponse> listPatrimoniosParaAlocacao() {
         return patrimonyRepository.findPatrimoniesToAllocation()
                .stream()
                .map(patrimonyMapper::toResponse)
                .collect(Collectors.toList());
    }

}
