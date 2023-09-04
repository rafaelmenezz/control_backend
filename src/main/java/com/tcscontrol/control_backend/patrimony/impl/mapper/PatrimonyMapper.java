package com.tcscontrol.control_backend.patrimony.impl.mapper;



import com.tcscontrol.control_backend.patrymony.model.Patrimony;
import com.tcscontrol.control_backend.patrymony.model.PatrimonyDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


public class PatrimonyMapper<ds> {

	 public PatrimonyDTO toDto(Patrimony pa){
	        if (pa == null) {
	            return null;
	        }
		
	        return new PatrimonyDTO(pa.getId_patrimonio(),
	                pa.getNr_serie(),
	                pa.getNr_ativo(), 
	                pa.getId_fornecedor(), 
	                pa.getNr_nf(),
	                pa.getDt_datanf(),
	                pa.getDt_aquisicao(),
	                pa.getVi_aquisicao(),
	                pa.getCh_status(),
	                pa.isFl_fixo());
	 }

		public Patrimony toEntity(@Valid @NotNull PatrimonyDTO patrimonioDTO) {
			if(patrimonioDTO == null){
	            return null;
	        }	
			// tirar esses null abaixo e criar lista para cada patrimonio  ter um fornecedor.
	    	Patrimony patrimony = new Patrimony(null, null, null, null, null, null, null, null, null, null, false);
	    	if (patrimonioDTO.id() != null) {
	    		patrimony.setId_patrimonio(patrimonioDTO.id());
	    	}
	    	patrimony.setNr_serie(patrimonioDTO.nr_serie());
	    	patrimony.setNr_ativo(patrimonioDTO.nr_ativo());
	    	patrimony.setId_fornecedor(patrimonioDTO.id_fornecedor());
	    	patrimony.setNr_nf(patrimonioDTO.nr_nf());
	    	patrimony.setDt_datanf(patrimonioDTO.dt_datanf());
	    	patrimony.setDt_aquisicao(patrimonioDTO.dt_aquisicao());
	    	patrimony.setVi_aquisicao(patrimonioDTO.vi_aquisicao());
	    	patrimony.setCh_status(patrimonioDTO.ch_status());
	    	patrimony.setFl_fixo(patrimonioDTO.fl_fixo());
	    	
	        return patrimony;
		}

		
}
