package com.tcscontrol.control_backend.allocation_patrimony.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.AllocationNegocio;
import com.tcscontrol.control_backend.allocation.model.dto.AllocationDTO;
import com.tcscontrol.control_backend.allocation.model.entity.Allocation;
import com.tcscontrol.control_backend.allocation_patrimony.AllocationPatrimonyNegocio;
import com.tcscontrol.control_backend.allocation_patrimony.AllocationPatrimonyRepository;
import com.tcscontrol.control_backend.allocation_patrimony.model.entity.AllocationPatrimony;
import com.tcscontrol.control_backend.department.impl.mapper.DepartmentMapper;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.enuns.SituationType;
import com.tcscontrol.control_backend.patrimony.PatrimonyNegocio;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;

import lombok.AllArgsConstructor;

@Component(value = "AllocationPatrimonyNegocio")
@AllArgsConstructor
public class AllocationPatrimonyImpl implements AllocationPatrimonyNegocio {


    AllocationPatrimonyRepository allocationPatrimonyRepository;
    DepartmentMapper departmentMapper;
    AllocationNegocio allocationNegocio;
    PatrimonyNegocio patrimonyNegocio;


    @Override
    public AllocationDTO create(AllocationDTO allocationDTO) {
            Department department = departmentMapper.toEntity(allocationDTO.departament());
            Allocation allocation = gravarAllocation(allocationDTO);
            List<Patrimony> patrimonios = obterPatrimonies(allocationDTO);
            List<AllocationPatrimony> aps = new ArrayList<>();

            atulizaPatrimoios(patrimonios);
            adicionaListaPatrimonios(patrimonios, aps, allocation, allocationDTO);
            aps = salvaAllocationPatrimony(aps);
            allocation.getPatrimonios().addAll(aps);
            allocationNegocio.salvaAllocation(allocation);
            List<PatrimonyDTO> pDTO = obtemListPatrimoniosDTO(aps);

            return new AllocationDTO(
                allocation.getId(), 
                allocationDTO.dtAlocacao(), 
                allocationDTO.observation(), 
                pDTO, 
                departmentMapper.toDTO(department));

    }

    @Override
    public AllocationDTO update(AllocationDTO allocationDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    private Allocation gravarAllocation(AllocationDTO allocationDTO){
        Allocation allocation = new Allocation();
        Department department = departmentMapper.toEntity(allocationDTO.departament());
        department.getAllocations().add(allocation);
        allocation.setDepartamento(department);
        allocation = allocationNegocio.salvaAllocation(allocation);

        return allocation;
    }

    private List<Patrimony> obterPatrimonies(AllocationDTO allocationDTO){
        if (UtilObjeto.isEmpty(allocationDTO.patrimonies())) {
            return new ArrayList<>();
        }
        return allocationDTO.patrimonies()
                  .stream()
                  .map(patrimonyNegocio::toEntity).collect(Collectors.toList());
    }

    private List<AllocationPatrimony> salvaAllocationPatrimony(List<AllocationPatrimony> ap){
        return allocationPatrimonyRepository.saveAll(ap);
    }

    private List<PatrimonyDTO> obtemListPatrimoniosDTO(List<AllocationPatrimony> aps){
        return aps.stream().map(c -> patrimonyNegocio.toDTO(c.getPatrimony())).collect(Collectors.toList());
    }

    private List<Patrimony> atulizaPatrimoios(List<Patrimony> patrimonies){
        if (UtilObjeto.isEmpty(patrimonies)) {
            return new ArrayList<>();
        }

        for (Patrimony p : patrimonies) {
            p.setTpSituacao(SituationType.ALOCADO);
        }
        return patrimonyNegocio.atulizaPatrimonios(patrimonies);
    }

    private void adicionaListaPatrimonios(List<Patrimony> patrimonies, List<AllocationPatrimony> allocationPatrimonies, Allocation allocation, AllocationDTO allocationDTO){

         for (Patrimony p : patrimonies) {
                AllocationPatrimony ap = new AllocationPatrimony();
                ap.setAllocation(allocation);
                ap.setDtAlocacao(UtilData.toDate(allocationDTO.dtAlocacao(), UtilData.FORMATO_DDMMAA));
                ap.setNmObservacao(allocationDTO.observation());
                ap.setPatrimony(p);
                p.setTpSituacao(SituationType.ALOCADO);
                p.getAllocations().add(ap);
                allocationPatrimonies.add(ap);
            }

    }
    
}
