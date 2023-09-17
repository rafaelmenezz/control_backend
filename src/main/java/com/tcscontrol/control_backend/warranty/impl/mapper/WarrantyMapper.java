package com.tcscontrol.control_backend.warranty.impl.mapper;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.patrimony.impl.mapper.PatrimonyMapper;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.utilitarios.UtilControl;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.warranty.model.dto.WarrantyDTO;
import com.tcscontrol.control_backend.warranty.model.entity.Warranty;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class WarrantyMapper {

      PatrimonyMapper patrimonyMapper;
      
      public WarrantyDTO toDto(Warranty warranty){
            if (warranty == null) {
                  return null;
            }
            PatrimonyDTO patrimonyDTO = patrimonyMapper.toDto(warranty.getPatrimony());

            return new WarrantyDTO(
                  warranty.getId(),
                  warranty.getDsGarantia(),
                  warranty.getTypewWarranty().getValue(),
                  UtilData.toString(warranty.getDtValidade(), UtilData.FORMATO_DDMMAA),
                  patrimonyDTO
            );

      }

      public Warranty toEntity(WarrantyDTO warrantyDTO){

            if(warrantyDTO == null){
                  return null;
            }
            Warranty warranty = new Warranty();
            if (warrantyDTO.id() !=null) {
                  warranty.setId(warrantyDTO.id());
            }

            Patrimony patrimony = patrimonyMapper.toEntity(warrantyDTO.patrimonio());

            warranty.setDsGarantia(warrantyDTO.dsGarantia());
            warranty.setTypewWarranty(UtilControl.convertTypeWarrantyValue(warrantyDTO.tipoGarantia()));
            warranty.setDtValidade(UtilData.toDate(warrantyDTO.dtValidade(), UtilData.FORMATO_DDMMAA));
            warranty.setPatrimony(patrimony);
            
            return warranty;

      }
}
