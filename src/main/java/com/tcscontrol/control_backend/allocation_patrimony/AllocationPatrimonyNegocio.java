package com.tcscontrol.control_backend.allocation_patrimony;

import java.util.List;

import com.tcscontrol.control_backend.allocation_patrimony.model.entity.AllocationPatrimony;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;

public interface AllocationPatrimonyNegocio extends AllocationPatrimonyService  {

    String MSG_EMAIL_NOVA_ALOCACAO = "Sua alocação para <b> NM_DEPARTAMENTO </b> foi registrada com sucesso, segue os patrimônio(s) alocado(s):";
 
    List<Patrimony> obtemListPatrimonies(List<AllocationPatrimony> list);

    AllocationPatrimony pesquisaAllocationPatrimonyPorId(Long id);

}
