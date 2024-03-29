package com.tcscontrol.control_backend.request_patrimony.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.constructions.ConstructionNegocio;
import com.tcscontrol.control_backend.constructions.model.entity.Construction;
import com.tcscontrol.control_backend.enuns.SituationType;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.enviar_email.EmailNegocio;
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

    private RequestNegocio requestNegocio;
    private RequestPatrimonyRepository requestPatrimonyRepository;
    private ConstructionNegocio constructionNegocio;
    private PatrimonyNegocio patrimonyNegocio;
    private EmailNegocio emailNegocio;

    @Override
    public RequestResponse addNewRequest(RequestsDTO requestsDTO) {
        validaRequisicao(requestsDTO);
        validarDataManutencao(AGENDAR, requestsDTO);
        Requests requests = obtemRequests(requestsDTO.id());
        Construction construction = constructionNegocio.toEntity(requestsDTO.obra());
        requests.setConstruction(construction);
        List<Patrimony> patrimonies = patrimonyNegocio.toListEntity(requestsDTO.patrimonios());
        List<RequestPatrimony> rps = new ArrayList<>();
        for (Patrimony patrimony : patrimonies) {
            RequestPatrimony rp = new RequestPatrimony();
            rp.setDtPrevisaoRetirada(UtilData.toDate(requestsDTO.dtPrevisaoRetirada(), UtilData.FORMATO_DDMMAA));
            rp.setDtPrevisaoDevolucao(UtilData.toDate(requestsDTO.dtPrevisaoDevolucao(), UtilData.FORMATO_DDMMAA));
            rp.setRequests(requests);
            rp.setPatrimony(patrimony);
            patrimony.setTpSituacao(SituationType.DISPONIVEL);
            patrimony.getRequests().add(rp);
            rps.add(rp);
        }
        patrimonyNegocio.atulizaPatrimonios(patrimonies);
        requests.getPatrimonies().addAll(rps);
        emailNegocio.enviarEmailRequisicoes(requests, MSG_EMAIL_SOLICITAR_REQUISICAO, MSG_NOVA_REQUISICAO_ADMIN);
        return requestNegocio.toResponse(salvaRequests(requests));
    }

    @Override
    public RequestResponse toRemove(RequestsDTO requestsDTO) {
        validaRequisicao(requestsDTO);
        validarDataManutencao(RETIRAR, requestsDTO);
        Requests requests = obtemRequests(requestsDTO.id());
        List<RequestPatrimony> rps = requests.getPatrimonies();
        List<Patrimony> patrimonies = new ArrayList<>();
        for (RequestPatrimony rp : rps) {
            Patrimony patrimony = rp.getPatrimony();
            rp.setDtPrevisaoRetirada(UtilData.toDate(requestsDTO.dtPrevisaoRetirada(), UtilData.FORMATO_DDMMAA));
            rp.setDtRetirada(UtilData.toDate(requestsDTO.dtRetirada(), UtilData.FORMATO_DDMMAA));
            rp.setDtPrevisaoDevolucao(UtilData.toDate(requestsDTO.dtRetirada(), UtilData.FORMATO_DDMMAA));
            patrimony.setTpSituacao(SituationType.REGISTRADO);
            patrimony.getRequests().add(rp);

            patrimonies.add(patrimony);
        }
        patrimonyNegocio.atulizaPatrimonios(patrimonies);
        requestPatrimonyRepository.saveAll(rps);
        emailNegocio.enviarEmailRequisicoes(requests, MSG_EMAIL_RETIRADA_REQUISICAO, MSG_RETIRADA_REQUISICAO_ADMIN);
        return requestNegocio.toResponse(salvaRequests(requests));
    }

    @Override
    public RequestResponse giveBackPatrimony(RequestsDTO requestsDTO) {
        validaRequisicao(requestsDTO);
        validarDataManutencao(FINALIZAR, requestsDTO);
        Requests requests = obtemRequests(requestsDTO.id());
        List<RequestPatrimony> rps = requests.getPatrimonies();
        List<Patrimony> patrimonies = new ArrayList<>();
        for (RequestPatrimony rp : rps) {
            Patrimony patrimony = rp.getPatrimony();
            if (!SituationType.REGISTRADO.equals(patrimony.getTpSituacao())) {
                 throw new IllegalRequestException(MSG_ERRO_PATRIMONIO_NÃO_DISPONIVEL.replace(NM_PATRIMONIO, patrimony.getNmPatrimonio()));
            }
            rp.setDtPrevisaoRetirada(UtilData.toDate(requestsDTO.dtPrevisaoRetirada(), UtilData.FORMATO_DDMMAA));
            rp.setDtPrevisaoDevolucao(UtilData.toDate(requestsDTO.dtPrevisaoDevolucao(), UtilData.FORMATO_DDMMAA));
            rp.setDtRetirada(UtilData.toDate(requestsDTO.dtRetirada(), UtilData.FORMATO_DDMMAA));
            rp.setDtDevolucao(new Date());
            rp.setRequests(requests);
            rp.setPatrimony(patrimony);
            rp.setStatus(Status.INACTIVE);
            patrimony.setTpSituacao(SituationType.DISPONIVEL);
            patrimonies.add(patrimony);
        }
        patrimonyNegocio.atulizaPatrimonios(patrimonies);
        requestPatrimonyRepository.saveAll(rps);
        emailNegocio.enviarEmailRequisicoes(requests, MSG_EMAIL_DEVOLUCAO_REQUISICAO, MSG_DEVOLUCAO_REQUISICAO_ADMIN);
        return requestNegocio.toResponse(salvaRequests(requests));

    }

    @Override
    public RequestResponse cancel(RequestsDTO requestsDTO) {
        Requests requests = obtemRequests(requestsDTO.id());
        Construction construction = constructionNegocio.toEntity(requestsDTO.obra());
        requests.setConstruction(construction);
        List<Patrimony> patrimonies = patrimonyNegocio.toListEntity(requestsDTO.patrimonios());
        List<RequestPatrimony> rps = new ArrayList<>();

        for (Patrimony patrimony : patrimonies) {
            RequestPatrimony rp = new RequestPatrimony();

            rp.setDtPrevisaoRetirada(null);
            rp.setDtRetirada(null);
            rp.setDtPrevisaoDevolucao(null);
            rp.setDtDevolucao(null);
            rp.setRequests(requests);
            rp.setPatrimony(patrimony);
            rp.setStatus(Status.INACTIVE);
            patrimony.setTpSituacao(SituationType.DISPONIVEL);
            rps.add(rp);
        }
        patrimonyNegocio.atulizaPatrimonios(patrimonies);
        requests.getPatrimonies().clear();
        requests.getPatrimonies().addAll(rps);
        emailNegocio.enviarEmailRequisicoes(requests, MSG_EMAIL_REJEITADA_REQUISICAO, MSG_REJEITA_REQUISICAO_ADMIN);
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

    private void validaRequisicao(RequestsDTO requestsDTO) {
        if (UtilObjeto.isEmpty(requestsDTO.patrimonios())) {
            throw new IllegalRequestException("Nenhum patrimônio foi informado!");
        }
        if (UtilObjeto.isEmpty(requestsDTO.obra())) {
            throw new IllegalRequestException("Obra não informado!");
        }
    }


    private void validarDataManutencao(Integer action, RequestsDTO requestsDTO) {
        switch (action) {
            case 1:
                geraErroObjetoNulo(requestsDTO.dtPrevisaoRetirada(), MSG_ERRO_DATA_PREVISAO_NAO_INFORMADA);
                break;
            case 2:
                geraErroObjetoNulo(requestsDTO.dtPrevisaoRetirada(), MSG_ERRO_DATA_PREVISAO_NAO_INFORMADA);
                geraErroObjetoNulo(requestsDTO.dtRetirada(), MSG_ERRO_DATA_INICIO_NAO_INFORMADA);
                break;
            case 3:
                geraErroObjetoNulo(requestsDTO.dtPrevisaoRetirada(), MSG_ERRO_DATA_PREVISAO_NAO_INFORMADA);
                geraErroObjetoNulo(requestsDTO.dtRetirada(), MSG_ERRO_DATA_INICIO_NAO_INFORMADA);
                geraErroObjetoNulo(requestsDTO.dtDevolucao(), MSG_ERRO_DATA_FIM_NAO_INFORMADA);
            default:
                break;
        }
    }

    private void geraErroObjetoNulo(Object object, String msg) {
        if (UtilObjeto.isEmpty(object)) {
            throw new IllegalRequestException(msg);
        }
    }

}
