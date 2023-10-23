package com.tcscontrol.control_backend.request_patrimony.impl.mapper;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.request_patrimony.model.dto.RequestPatrimonyDTO;
import com.tcscontrol.control_backend.request_patrimony.model.entity.RequestPatrimony;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RequestPatrimonyMapper {
    
    private PatrimonyMapper patrimonyMapper;


    public RequestPatrimony toEntity(RequestPatrimonyDTO rpDTO){
        if( rpDTO == null){
            return null;
        }
        RequestPatrimony rp = new RequestPatrimony();
        if (UtilObjeto.isNotEmpty(rpDTO.id())) {
            rp.setId(rpDTO.id());
        }
        rp.setDtDevolucao(UtilData.toDate(rpDTO.dtDevolucao(), UtilData.FORMATO_DDMMAA));
        rp.setDtRetirada(UtilData.toDate(rpDTO.dtRetirada(), UtilData.FORMATO_DDMMAA));
        rp.setPatrimony(patrimonyMapper.toEntity(rpDTO.patrimonios()));
        return rp;

    }


    public RequestPatrimonyDTO toDTO(RequestPatrimony rp){
        if(UtilObjeto.isEmpty(rp)){
            return null;
        }
        return new RequestPatrimonyDTO(
            rp.getId(), 
            UtilData.toString(rp.getDtRetirada(), UtilData.FORMATO_DDMMAA), 
            UtilData.toString(rp.getDtDevolucao(),UtilData.FORMATO_DDMMAA),  
            patrimonyMapper.toDto(rp.getPatrimony()));


    }

}
