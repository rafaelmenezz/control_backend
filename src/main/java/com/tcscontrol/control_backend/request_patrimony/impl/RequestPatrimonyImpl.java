package com.tcscontrol.control_backend.request_patrimony.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.constructions.ConstructionNegocio;
import com.tcscontrol.control_backend.constructions.model.entity.Construction;
import com.tcscontrol.control_backend.enuns.SituationType;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.exception.IllegalRequestException;
import com.tcscontrol.control_backend.patrimony.PatrimonyNegocio;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.request_patrimony.RequestPatrimonyNegocio;
import com.tcscontrol.control_backend.request_patrimony.RequestPatrimonyRepository;
import com.tcscontrol.control_backend.request_patrimony.model.entity.RequestPatrimony;
import com.tcscontrol.control_backend.requests.RequestNegocio;
import com.tcscontrol.control_backend.requests.model.dto.RequestResponse;
import com.tcscontrol.control_backend.requests.model.dto.RequestsDTO;
import com.tcscontrol.control_backend.requests.model.entity.Requests;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;

import lombok.AllArgsConstructor;

@Component(value = "requestPatrimonyNegocio")
@AllArgsConstructor
public class RequestPatrimonyImpl implements RequestPatrimonyNegocio {

    private RequestPatrimonyRepository requestPatrimonyRepository;
    private RequestNegocio requestNegocio;
    private ConstructionNegocio constructionNegocio;
    private PatrimonyNegocio patrimonyNegocio;

    @Override
    public RequestResponse addNewRequest(RequestsDTO requestsDTO) {
        validaRequisicao(requestsDTO);
        Requests requests = obtemRequests(requestsDTO.id());
        Construction construction = constructionNegocio.toEntity(requestsDTO.obra());
        requests.setConstruction(construction);
        List<Patrimony> patrimonies = patrimonyNegocio.toListEntity(requestsDTO.patrimonios());
        List<RequestPatrimony> rps = new ArrayList<>();
        for (Patrimony patrimony : patrimonies) {
            RequestPatrimony rp = new RequestPatrimony();
            rp.setDtRetirada(UtilData.toDate(requestsDTO.dtRetirada(), UtilData.FORMATO_DDMMAA));
            rp.setRequests(requests);
            rp.setPatrimony(patrimony);
            patrimony.setTpSituacao(SituationType.REGISTRADO);
            rps.add(rp);
        }
        patrimonyNegocio.atulizaPatrimonios(patrimonies);
        requests.getPatrimonies().addAll(rps);
        return requestNegocio.toResponse(salvaRequests(requests));
    }

    @Override
    public RequestResponse giveBackPatrimony(RequestsDTO requestsDTO) {
        validaRequisicao(requestsDTO);
        Requests requests = obtemRequests(requestsDTO.id());
        Construction construction = constructionNegocio.toEntity(requestsDTO.obra());
        requests.setConstruction(construction);
        List<Patrimony> patrimonies = patrimonyNegocio.toListEntity(requestsDTO.patrimonios());
        List<RequestPatrimony> rps = new ArrayList<>();
        for (Patrimony patrimony : patrimonies) {
            RequestPatrimony rp = new RequestPatrimony();
            rp.setDtRetirada(UtilData.toDate(requestsDTO.dtRetirada(), UtilData.FORMATO_DDMMAA));
            rp.setDtDevolucao(UtilData.toDate(requestsDTO.dtDevolucao(), UtilData.FORMATO_DDMMAA));
            rp.setRequests(requests);
            rp.setPatrimony(patrimony);
            rp.setStatus(Status.INACTIVE);
            patrimony.setTpSituacao(SituationType.DISPONIVEL);
            rps.add(rp);
        }
        patrimonyNegocio.atulizaPatrimonios(patrimonies);
        requests.getPatrimonies().addAll(rps);
        return requestNegocio.toResponse(salvaRequests(requests));

    }


    private Requests obtemRequests(Long id) {
        if (UtilObjeto.isEmpty(id)) {
            return new Requests();
        }
        return requestNegocio.pesquisaRequests(id);

    }

    private Requests salvaRequests(Requests requests) {
        return requestNegocio.salvaRequests(requests);
    }

    private RequestPatrimony saveNewRequestPatrimony(RequestPatrimony requestPatrimony) {
        return requestPatrimonyRepository.save(requestPatrimony);
    }

    private List<RequestPatrimony> saveAllRequestPatrimonies(List<RequestPatrimony> listRequestPatrimonies) {
        return requestPatrimonyRepository.saveAllAndFlush(listRequestPatrimonies);
    }

    private void validaRequisicao(RequestsDTO requestsDTO){
        if (UtilObjeto.isEmpty(requestsDTO.dtRetirada())) {
            throw new IllegalRequestException("Data de Retirada não informada!");
        }
        if (UtilObjeto.isEmpty(requestsDTO.patrimonios())) {
            throw new IllegalRequestException("Nenhum patrimônio foi informado!");
        }
        if (UtilObjeto.isEmpty(requestsDTO.obra())) {
            throw new IllegalRequestException("Obra não informado!");
        }
        
    }


}
