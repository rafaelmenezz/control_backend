package com.tcscontrol.control_backend.allocation_patrimony.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.AllocationNegocio;
import com.tcscontrol.control_backend.allocation.model.dto.AllocationDTO;
import com.tcscontrol.control_backend.allocation.model.dto.AllocationResponse;
import com.tcscontrol.control_backend.allocation.model.entity.Allocation;
import com.tcscontrol.control_backend.allocation_patrimony.AllocationPatrimonyNegocio;
import com.tcscontrol.control_backend.allocation_patrimony.AllocationPatrimonyRepository;
import com.tcscontrol.control_backend.allocation_patrimony.model.dto.AllocationPatrimonyDTO;
import com.tcscontrol.control_backend.allocation_patrimony.model.entity.AllocationPatrimony;
import com.tcscontrol.control_backend.department.impl.mapper.DepartmentMapper;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.enuns.SituationType;
import com.tcscontrol.control_backend.exception.IllegalRequestException;
import com.tcscontrol.control_backend.patrimony.PatrimonyNegocio;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;

import lombok.AllArgsConstructor;

@Component(value = "AllocationPatrimonyNegocio")
@AllArgsConstructor
public class AllocationPatrimonyImpl implements AllocationPatrimonyNegocio {


    AllocationPatrimonyRepository allocationPatrimonyRepository;
    AllocationPatrimonyMapper allocationPatrimonyMapper;
    DepartmentMapper departmentMapper;
    AllocationNegocio allocationNegocio;
    PatrimonyNegocio patrimonyNegocio;


    @Override
    public AllocationResponse create(AllocationDTO allocationDTO) {
            validaAlocacao(allocationDTO);
            Allocation allocation = gravarAllocation(allocationDTO);
            List<Patrimony> patrimonios = obterPatrimonies(allocationDTO);
            List<AllocationPatrimony> aps = new ArrayList<>();
            validaAlocacaoPatrimonio(patrimonios);
            atulizaPatrimoios(patrimonios);
            adicionaListaPatrimonios(patrimonios, aps, allocation, allocationDTO);
            aps = salvaAllocationPatrimony(aps);
            allocation.getPatrimonios().addAll(aps);
            allocationNegocio.salvaAllocation(allocation);
            List<AllocationPatrimonyDTO> apDTO = allocation.getPatrimonios().stream().map(allocationPatrimonyMapper::toDTO).collect(Collectors.toList()); 

            return new AllocationResponse(
                allocation.getId(),
                departmentMapper.toDTO(allocation.getDepartamento()),
                apDTO);

    }

        @Override
        public AllocationResponse giveBackPatrimony(AllocationDTO allocationDTO) {
            validaAlocacao(allocationDTO);
            Allocation allocation = gravarAllocation(allocationDTO);
            List<Patrimony> patrimonios = obterPatrimonies(allocationDTO);
            List<AllocationPatrimony> aps = new ArrayList<>();
            validaAlocacaoPatrimonio(patrimonios);
            atulizaPatrimoios(patrimonios);
            adicionaListaPatrimonios(patrimonios, aps, allocation, allocationDTO);
            aps = salvaAllocationPatrimony(aps);
            allocation.getPatrimonios().addAll(aps);
            allocationNegocio.salvaAllocation(allocation);
            List<AllocationPatrimonyDTO> apDTO = allocation.getPatrimonios().stream().map(allocationPatrimonyMapper::toDTO).collect(Collectors.toList());

             return new AllocationResponse(
                allocation.getId(),
                departmentMapper.toDTO(allocation.getDepartamento()),
                apDTO);
        }

    @Override
    public AllocationResponse update(Long id, AllocationDTO allocationDTO) {
        Allocation allocation = allocationNegocio.pesquisaAllocationPorId(id);
        List<Patrimony> patrimonios = obterPatrimonies(allocationDTO);
        List<AllocationPatrimony> aps = new ArrayList<>();
        atulizaPatrimoios(patrimonios);
        adicionaListaPatrimonios(patrimonios, aps, allocation, allocationDTO);
        aps = salvaAllocationPatrimony(aps);
        allocation.setDepartamento(departmentMapper.toEntity(allocationDTO.departament()));
        allocation.getPatrimonios().clear();
        allocation.getPatrimonios().addAll(aps);
        allocationNegocio.salvaAllocation(allocation);

        List<AllocationPatrimonyDTO> apDTO = allocation.getPatrimonios().stream().map(allocationPatrimonyMapper::toDTO).collect(Collectors.toList()); 

        return new AllocationResponse(
                allocation.getId(),
                departmentMapper.toDTO(allocation.getDepartamento()),
                apDTO); 
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
                if (UtilObjeto.isEmpty(ap.getDtAlocacao())) {
                  ap.setDtAlocacao(UtilData.toDate(allocationDTO.dtAlocacao(), UtilData.FORMATO_DDMMAA));  
                }
                ap.setDtDevolucao(UtilData.toDate(allocationDTO.dtAlocacao(), UtilData.FORMATO_DDMMAA));
                ap.setNmObservacao(allocationDTO.observation());
                ap.setPatrimony(p);
                p.setTpSituacao(SituationType.ALOCADO);
                p.getAllocations().add(ap);
                allocationPatrimonies.add(ap);
            }

    }

    private void validaAlocacaoPatrimonio(List<Patrimony> list ){

        for (Patrimony p : list) {
            if (!SituationType.DISPONIVEL.equals(p.getTpSituacao())) {
                throw new IllegalRequestException(p.getNmPatrimonio() + " não está disponível!");
            }
        }
    }

        private void validaAlocacao(AllocationDTO allocationDTO){
        if (UtilObjeto.isEmpty(allocationDTO.dtAlocacao())) {
            throw new IllegalRequestException("Data de Retirada não informada!");
        }
        if (UtilObjeto.isEmpty(allocationDTO.patrimonies())) {
            throw new IllegalRequestException("Nenhum patrimônio foi informado!");
        }
        if (UtilObjeto.isEmpty(allocationDTO.departament())) {
            throw new IllegalRequestException("Obra não informado!");
        }
        
    }


    
}
