package com.tcscontrol.control_backend.allocation_patrimony.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.enviar_email.EmailNegocio;
import com.tcscontrol.control_backend.exception.IllegalRequestException;
import com.tcscontrol.control_backend.patrimony.PatrimonyNegocio;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;

import lombok.AllArgsConstructor;

@Component(value = "allocationPatrimonyNegocio")
@AllArgsConstructor
public class AllocationPatrimonyNegocioImpl implements AllocationPatrimonyNegocio {

    private AllocationPatrimonyRepository allocationPatrimonyRepository;
    private AllocationPatrimonyMapper allocationPatrimonyMapper;
    private DepartmentMapper departmentMapper;
    private AllocationNegocio allocationNegocio;
    private PatrimonyNegocio patrimonyNegocio;
    private EmailNegocio emailNegocio;

    @Override
    public AllocationResponse create(AllocationDTO allocationDTO) {
        validaAlocacao(allocationDTO);
        Allocation allocation = gravarAllocation(allocationDTO);
        List<Patrimony> patrimonios = obterPatrimonies(allocationDTO);
        verificaPatrimonio(patrimonios);

        List<AllocationPatrimony> aps = new ArrayList<>();
        validaAlocacaoPatrimonio(patrimonios);
        atulizaPatrimoios(patrimonios);
        adicionaListaPatrimonios(patrimonios, aps, allocation, allocationDTO);
        aps = salvaAllocationPatrimony(aps);
        allocation.getPatrimonios().addAll(aps);

        User usuario = allocation.getDepartamento().getUser();
        allocation = allocationNegocio.salvaAllocation(allocation);
        emailNegocio.enviarEmailNovaAlocacao(usuario, allocation.getDepartamento(),
                obtemListPatrimonies(allocation.getPatrimonios()));

        List<AllocationPatrimonyDTO> apDTO = allocation.getPatrimonios().stream().map(allocationPatrimonyMapper::toDTO)
                .collect(Collectors.toList());

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

        User usuario = allocation.getDepartamento().getUser();
        allocation = allocationNegocio.salvaAllocation(allocation);
        emailNegocio.enviarEmailDevolucaoAlocacao(usuario, obtemListPatrimonies(allocation.getPatrimonios()));

        List<AllocationPatrimonyDTO> apDTO = allocation.getPatrimonios().stream().map(allocationPatrimonyMapper::toDTO)
                .collect(Collectors.toList());

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

        List<AllocationPatrimonyDTO> apDTO = allocation.getPatrimonios().stream().map(allocationPatrimonyMapper::toDTO)
                .collect(Collectors.toList());

        return new AllocationResponse(
                allocation.getId(),
                departmentMapper.toDTO(allocation.getDepartamento()),
                apDTO);
    }

    private Allocation gravarAllocation(AllocationDTO allocationDTO) {
        Allocation allocation = new Allocation();
        Department department = departmentMapper.toEntity(allocationDTO.departament());
        department.getAllocations().add(allocation);
        allocation.setDepartamento(department);

        return allocationNegocio.salvaAllocation(allocation);
    }

    private List<Patrimony> obterPatrimonies(AllocationDTO allocationDTO) {
        if (UtilObjeto.isEmpty(allocationDTO.patrimonies())) {
            return new ArrayList<>();
        }
        return allocationDTO.patrimonies()
                .stream()
                .map(patrimonyNegocio::toEntity).collect(Collectors.toList());
    }

    private List<AllocationPatrimony> salvaAllocationPatrimony(List<AllocationPatrimony> ap) {
        return allocationPatrimonyRepository.saveAll(ap);
    }

    private List<Patrimony> atulizaPatrimoios(List<Patrimony> patrimonies) {
        if (UtilObjeto.isEmpty(patrimonies)) {
            return new ArrayList<>();
        }
        for (Patrimony p : patrimonies) {
            p.setTpSituacao(SituationType.ALOCADO);
        }
        return patrimonyNegocio.atulizaPatrimonios(patrimonies);
    }

    private void adicionaListaPatrimonios(List<Patrimony> patrimonies, List<AllocationPatrimony> allocationPatrimonies,
            Allocation allocation, AllocationDTO allocationDTO) {

        for (Patrimony p : patrimonies) {
            AllocationPatrimony ap = new AllocationPatrimony();
            ap.setAllocation(allocation);
            if (UtilObjeto.isEmpty(ap.getDtAlocacao())) {
                ap.setDtAlocacao(UtilData.toDate(allocationDTO.dtAlocacao(), UtilData.FORMATO_DDMMAA));
                p.setTpSituacao(SituationType.ALOCADO);
            }
            if (UtilObjeto.isNotEmpty(allocationDTO.dtDevolucao())) {
                ap.setDtDevolucao(UtilData.toDate(allocationDTO.dtAlocacao(), UtilData.FORMATO_DDMMAA));
                ap.setStatus(Status.INACTIVE);
                p.setTpSituacao(SituationType.DISPONIVEL);
            }
            ap.setNmObservacao(allocationDTO.observation());
            ap.setPatrimony(p);
            p.getAllocations().add(ap);
            allocationPatrimonies.add(ap);
        }

    }

    @Override
    public AllocationPatrimony pesquisaAllocationPatrimonyPorId(Long id) {
        return allocationPatrimonyRepository.pesquisAllocationPatrimonyPorIdPatrimonio(id);
    }

    private void validaAlocacaoPatrimonio(List<Patrimony> list) {

        for (Patrimony p : list) {
            if (SituationType.EM_MANUTENCAO.equals(p.getTpSituacao())) {
                throw new IllegalRequestException(p.getNmPatrimonio() + " esta em manutenção!");
            }
        }
    }

    private void validaAlocacao(AllocationDTO allocationDTO) {
        if (UtilObjeto.isEmpty(allocationDTO.dtAlocacao())) {
            throw new IllegalRequestException("Data de alocação não informada!");
        }
        if (UtilObjeto.isEmpty(allocationDTO.patrimonies())) {
            throw new IllegalRequestException("Nenhum patrimônio foi informado!");
        }
        if (UtilObjeto.isEmpty(allocationDTO.departament())) {
            throw new IllegalRequestException("Departamento não informado!");
        }

    }

    public List<Patrimony> obtemListPatrimonies(List<AllocationPatrimony> list) {
        if (UtilObjeto.isEmpty(list)) {
            return null;
        }
        List<Patrimony> retorno = new ArrayList<>();
        for (AllocationPatrimony allocationPatrimony : list) {
            retorno.add(allocationPatrimony.getPatrimony());
        }

        return retorno;

    }

    private void verificaPatrimonio(List<Patrimony> patrimonios) {

        List<Long> ids = new ArrayList<>();
        for (Patrimony patrimony : patrimonios) {
            ids.add(patrimony.getId());
        }
        List<AllocationPatrimony> aps = allocationPatrimonyRepository.getList(ids);
        if (!aps.isEmpty()) {
            devolverPatrimonios(aps);   
        }
    }

    private void devolverPatrimonios(List<AllocationPatrimony> lista) {
        List<AllocationPatrimony> aps = new ArrayList<>();
        for (AllocationPatrimony allocationPatrimony : lista) {
            allocationPatrimony.setDtDevolucao(new Date());
            allocationPatrimony.setStatus(Status.INACTIVE);
            aps.add(allocationPatrimony);
        }
        aps = allocationPatrimonyRepository.saveAllAndFlush(aps);
    }

}
