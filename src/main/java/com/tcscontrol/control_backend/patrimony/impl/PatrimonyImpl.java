package com.tcscontrol.control_backend.patrimony.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.patrimony.PatrimonyNegocio;
import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrymony.model.Patrimony;
import com.tcscontrol.control_backend.patrymony.model.PatrimonyDTO;
import com.tcscontrol.control_backend.patrymony.model.PatrimonyRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Component(value = "PatrimonyNegocio")
public class PatrimonyImpl<ds> implements PatrimonyNegocio {

   private final PatrimonyRepository patrimonyRepository;
   private final PatrimonyMapper<ds> patrimonyMapper;
 
   public  PatrimonyImpl(PatrimonyRepository patrimonyRepository, PatrimonyMapper<ds> patrimonyMapper) {
       this.patrimonyRepository = patrimonyRepository;
       this.patrimonyMapper = patrimonyMapper;
   }
   
 @Override
public List<PatrimonyDTO> list() {
 return patrimonyRepository.findAll()
 .stream()
 .map(patrimonyMapper::toDto)
 .collect(Collectors.toList());
 }

 
 @Override
 public PatrimonyDTO findById(@NotNull @Positive Long id) {
     return patrimonyRepository.findById(id)
         .map(patrimonyMapper::toDto)
         .orElseThrow(() -> new RecordNotFoundException(id)); 
 }

 @Override
public PatrimonyDTO criarPatrimonio(@Valid @NotNull PatrimonyDTO patrimonioDTO) {
     return patrimonyMapper.toDto(patrimonyRepository.save(patrimonyMapper.toEntity(patrimonioDTO)));

}
 

@Override
public PatrimonyDTO update(Long id, @Valid PatrimonyDTO patrimonyDTO) {
	return patrimonyRepository.findById(id)
	.map(recordfound -> {
	Patrimony patrimony = patrimonyMapper.toEntity(patrimonyDTO);
	recordfound.setId_fornecedor(patrimonyDTO.id_fornecedor());
	recordfound.setNr_ativo(patrimonyDTO.nr_ativo());
	recordfound.setDt_aquisicao(patrimonyDTO.dt_aquisicao());
	recordfound.setDt_datanf(patrimonyDTO.dt_aquisicao());
	recordfound.setCh_status(patrimonyDTO.ch_status());
	recordfound.setFl_fixo(patrimonyDTO.fl_fixo());
    return patrimonyMapper.toDto(patrimonyRepository.save(recordfound));
    }).orElseThrow(() -> new RecordNotFoundException(id));
}


@Override
public void delete(@NotNull @Positive Long id) {
	patrimonyRepository.delete(patrimonyRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));;
}
    

}
