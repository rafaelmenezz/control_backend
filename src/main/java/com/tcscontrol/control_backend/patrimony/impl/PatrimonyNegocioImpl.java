package com.tcscontrol.control_backend.patrimony.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.AllocationNegocio;
import com.tcscontrol.control_backend.allocation.model.entity.Allocation;
import com.tcscontrol.control_backend.enuns.SituationType;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.exception.IllegalRequestException;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.patrimony.LossTheftRepository;
import com.tcscontrol.control_backend.patrimony.PatrimonyNegocio;
import com.tcscontrol.control_backend.patrimony.PatrimonyRepository;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyHistoricDTO;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyResponse;
import com.tcscontrol.control_backend.patrimony.model.entity.LossTheft;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.pessoa.fornecedor.Fornecedor;
import com.tcscontrol.control_backend.pessoa.fornecedor.FornecedorNegocio;
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
    private LossTheftRepository lossTheftRepository;

    @Override
    public List<PatrimonyResponse> list() {
        return patrimonyRepository.findByTpStatus(Status.ACTIVE)
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
        return patrimonyMapper.toResponse(salveNewPatrimony(patrimonyDTO));
    }

    @Override
    public PatrimonyResponse update(Long id, PatrimonyDTO patrimonyDto) {
        return patrimonyRepository.findById(id)
                .map(recordFound -> {
                    Patrimony patrimony = patrimonyMapper.toEntity(patrimonyDto);
                    Fornecedor fornecedor = fornecedorNegocio.pesquisaFornecedorCnpj(patrimonyDto.nrCnpj());
                    if (fornecedor == null) {
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
                    recordFound.setNmPatrimonio(patrimonyDto.nmPatrimonio());
                    recordFound.setNmDescricao(patrimonyDto.nmDescricao());
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
    public List<PatrimonyResponse> search(String nmPatrimonio, String nrSerie, String dsPatrimonio, String nrCnpj,
            String nmFornecedor, String dtAquisicao) {

        Date dt = null;
        if (!UtilObjeto.isEmpty(dtAquisicao)) {
            dt = UtilData.toDate(dtAquisicao, UtilData.FORMATO_DDMMAA);
        }

        return patrimonyRepository
                .findByNmPatrimonioContainingOrNrSerieContainingOrNmDescricaoContainingOrFornecedorNrCnpjContainingOrFornecedorNmNameContainingOrDtAquisicaoContaining(
                        nmPatrimonio, nrSerie, dsPatrimonio, nrCnpj, nmFornecedor, dt)
                .stream()
                .map(patrimonyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Patrimony> obtemPatrimonies(Long[] ids) {
        return patrimonyRepository.findAllById(Arrays.asList(ids));

    }

    @Override
    public Allocation actualAlocation(Long id) {
        return allocationNegocio.obtemLocalizacaoPatrimonio(id);
    }

    @Override
    public List<PatrimonyResponse> listPatrimoniesFixOrNotFix(String nmPatrimony, Boolean fixo) {
        return obtemPatrimoniosPorTipo(nmPatrimony, fixo);
    }

    private List<PatrimonyResponse> obtemPatrimoniosPorTipo(String nmPatrimony, Boolean fixo) {
        return patrimonyRepository.findPatrimoniesToAllocation("%" + nmPatrimony + "%", fixo)
                .stream()
                .map(patrimonyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PatrimonyDTO toDTO(Patrimony patrimony) {
        return patrimonyMapper.toDto(patrimony);
    }

    @Override
    public Patrimony toEntity(PatrimonyDTO patrimonyDTO) {
        return patrimonyMapper.toEntity(patrimonyDTO);
    }

    @Override
    public List<Patrimony> atulizaPatrimonios(List<Patrimony> patrimonies) {
        return patrimonyRepository.saveAllAndFlush(patrimonies);
    }

    @Override
    public List<Patrimony> toListEntity(List<PatrimonyDTO> patrimonyDTOs) {
        return patrimonyDTOs.stream().map(patrimonyMapper::toEntity).collect(Collectors.toList());
    }

    @Override
    public PatrimonyDTO addLostThieftPatrimony(PatrimonyDTO patrimonyDTO) {
        Patrimony patrimony = patrimonyMapper.toEntity(patrimonyDTO);
        if (isNotValidLowPatrimony(patrimonyDTO, patrimony)) {
            throw new IllegalRequestException(MSG_EXEPTION_ERRO_LOSS_THIEF_INVALID);
        }

        LossTheft lossTheft = new LossTheft();

        lossTheft.setDtLost(new Date());
        lossTheft.setPatrimony(patrimony);
        patrimony.setLossTheft(lossTheftRepository.save(lossTheft));

        patrimony.setTpSituacao(SituationType.PERDA_ROUBO);
        patrimony.setTpStatus(Status.INACTIVE);
        atualizaPatrimonio(patrimony);

        return patrimonyMapper.toDto(patrimony);
        
    }

    private Patrimony salveNewPatrimony(PatrimonyDTO patrimonyDTO) {
        Patrimony p = patrimonyMapper.toEntity(patrimonyDTO);
        p.setTpSituacao(SituationType.DISPONIVEL);

        return patrimonyRepository.save(p);
    }

    @Override
    public Patrimony atualizaPatrimonio(Patrimony patrimony) {
        return patrimonyRepository.save(patrimony);
    }

    private Boolean isNotValidLowPatrimony(PatrimonyDTO patrimonyDTO, Patrimony patrimony){
        
        if (UtilObjeto.isEmpty(patrimonyDTO)) 
            return false;
        if(UtilObjeto.isEmpty(patrimony.getLossTheft()))
            return false;
        if(UtilObjeto.isEmpty(patrimony.getLossTheft().getNmObservation()))
            return false;

        return true;
    }

    @Override
    public PatrimonyDTO findPatrimonyForConstruction(Long id) {
         return patrimonyRepository.findByIdPatrimonyToConstruction(id)
                .map(patrimonyMapper::toDto)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Override
    public PatrimonyDTO findPatrimonyForAllocartion(Long id) {
        return patrimonyRepository.findByIdPatrimonyToAllocation(id)
                .map(patrimonyMapper::toDto)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Override
    public PatrimonyHistoricDTO findHistoricPatrimony(Long id) {
        return patrimonyRepository.findById(id)
                .map(patrimonyMapper::toHistoricDto)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }


}
