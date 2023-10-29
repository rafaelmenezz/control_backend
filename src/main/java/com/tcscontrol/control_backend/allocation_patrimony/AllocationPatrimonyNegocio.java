package com.tcscontrol.control_backend.allocation_patrimony;

import java.util.List;

import com.tcscontrol.control_backend.allocation_patrimony.model.entity.AllocationPatrimony;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;

public interface AllocationPatrimonyNegocio extends AllocationPatrimonyService  {
 
    List<Patrimony> obtemListPatrimonies(List<AllocationPatrimony> list);

    AllocationPatrimony pesquisAllocationPatrimonyPorId(Long id);
}
