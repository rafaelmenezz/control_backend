package com.tcscontrol.control_backend.patrimony.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.enuns.SituationType;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.patrimony.PatrimonyNegocio;
import com.tcscontrol.control_backend.patrimony.PatrimonyRepository;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyResponse;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.pessoa.fornecedor.Fornecedor;
import com.tcscontrol.control_backend.pessoa.fornecedor.FornecedorNegocio;
import com.tcscontrol.control_backend.utilitarios.UtilControl;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;
import com.tcscontrol.control_backend.warranty.model.dto.WarrantyDTO;
import com.tcscontrol.control_backend.warranty.model.entity.Warranty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PatrimonyNegocioImpl implements PatrimonyNegocio {

    private PatrimonyRepository patrimonyRepository;
    private PatrimonyMapper patrimonyMapper;
    private FornecedorNegocio fornecedorNegocio;

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
        return patrimonyMapper.toResponse(salveNewPatrimony(patrimonyDTO));
    }

    @Override
    public PatrimonyResponse update(Long id, PatrimonyDTO patrimonyDto) {
        Patrimony patrimonyToUpdate = patrimonyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

        updatePatrimonySituation(patrimonyToUpdate, patrimonyDto);
        updatePatrimonyFields(patrimonyToUpdate, patrimonyDto);
        updateWarranties(patrimonyToUpdate, patrimonyDto.warranties());

        Patrimony updatedPatrimony = patrimonyRepository.save(patrimonyToUpdate);
        return patrimonyMapper.toResponse(updatedPatrimony);
    }

    private void updatePatrimonyFields(Patrimony patrimonyToUpdate, PatrimonyDTO patrimonyDto) {
        Fornecedor fornecedor = fornecedorNegocio.pesquisaFornecedorCnpj(patrimonyDto.nrCnpj());
        patrimonyToUpdate.setNrSerie(patrimonyDto.nrSerie());
        patrimonyToUpdate.setNmPatrimonio(patrimonyDto.nmPatrimonio());
        patrimonyToUpdate.setNmDescricao(patrimonyDto.nmDescricao());
        patrimonyToUpdate.setNrNotaFiscal(patrimonyDto.nrNF());
        patrimonyToUpdate.setDtNotaFiscal(UtilData.toDate(patrimonyDto.dtNF(), UtilData.FORMATO_DDMMAA));
        patrimonyToUpdate.setDtAquisicao(UtilData.toDate(patrimonyDto.dtAquisicao(), UtilData.FORMATO_DDMMAA));
        patrimonyToUpdate.setVlAquisicao(patrimonyDto.vlAquisicao());
        patrimonyToUpdate.setFixo(patrimonyDto.fixo());
        patrimonyToUpdate.setFornecedor(fornecedor);

    }

    private void updatePatrimonySituation(Patrimony patrimonyToUpdate, PatrimonyDTO patrimonyDto) {
        if (patrimonyToUpdate.getFixo() && (patrimonyToUpdate.getTpSituacao() == SituationType.FIXO)
                && !patrimonyDto.fixo()) {
            patrimonyToUpdate.setTpSituacao(SituationType.DISPONIVEL);
        }
        if (!patrimonyToUpdate.getFixo() && (patrimonyToUpdate.getTpSituacao() == SituationType.DISPONIVEL)
                && patrimonyDto.fixo()) {
            patrimonyToUpdate.setTpSituacao(SituationType.FIXO);
        }

        if (patrimonyDto.fixo()) {
            if (patrimonyToUpdate.getTpSituacao() != SituationType.FIXO && !patrimonyToUpdate.getFixo()) {
                throw new IllegalArgumentException("Patrimônio não pode ser alterado!");
            }
        } else {
            if (patrimonyToUpdate.getTpSituacao() != SituationType.DISPONIVEL && patrimonyToUpdate.getFixo()) {
                throw new IllegalArgumentException("Patrimônio não pode ser alterado!");
            }
        }
    }

    private void updateWarranties(Patrimony patrimonyToUpdate, List<WarrantyDTO> warrantyDTOs) {
        List<Warranty> warranties = warrantyDTOs.stream()
                .map(warrantyDto -> {
                    return new Warranty(
                            warrantyDto.id(),
                            warrantyDto.dsGarantia(),
                            UtilData.toDate(warrantyDto.dtValidade(), UtilData.FORMATO_DDMMAA),
                            UtilControl.convertTypeWarrantyValue(warrantyDto.tipoGarantia()),
                            patrimonyToUpdate);
                })
                .collect(Collectors.toList());

        patrimonyToUpdate.getWarrantys().clear();
        patrimonyToUpdate.getWarrantys().addAll(warranties);
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

    private Patrimony salveNewPatrimony(PatrimonyDTO patrimonyDTO) {
        Patrimony p = patrimonyMapper.toEntity(patrimonyDTO);
        return patrimonyRepository.save(p);
    }

    @Override
    public Patrimony atualizaPatrimonio(Patrimony patrimony) {
        return patrimonyRepository.save(patrimony);
    }

}
