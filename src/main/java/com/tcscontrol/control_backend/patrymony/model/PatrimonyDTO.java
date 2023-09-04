package com.tcscontrol.control_backend.patrymony.model;


import lombok.Data;

public record PatrimonyDTO(
   
      Integer id,
      Integer nr_serie,
      Integer nr_ativo,
      Integer id_fornecedor,
      Integer nr_nf,
      Data dt_datanf,
      Data dt_aquisicao,
      Double vi_aquisicao,
      Integer ch_status,
      boolean fl_fixo
      
      	  ) {


}
