package com.tcscontrol.control_backend.requests.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.exception.IllegalRequestException;
import com.tcscontrol.control_backend.requests.RequestNegocio;
import com.tcscontrol.control_backend.requests.RequestsRepository;
import com.tcscontrol.control_backend.requests.impl.mapper.RequestMapper;
import com.tcscontrol.control_backend.requests.model.dto.RequestResponse;
import com.tcscontrol.control_backend.requests.model.entity.Requests;

import lombok.AllArgsConstructor;

@Component(value = "requestNegocio")
@AllArgsConstructor
public class RequestsImpl implements RequestNegocio {
    
    private RequestsRepository requestsRepository;
    private RequestMapper requestMapper;

    @Override
    public List<RequestResponse> list() {
       return listaRequicicoes().stream().map(requestMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public Requests salvaRequests(Requests requests){
        return salva(requests);
    }

    @Override
    public RequestResponse findById(Long id) {
        return requestMapper.toResponse(findbyId(id));
    }

    @Override
    public Requests pesquisaRequests(Long id) {
        return findbyId(id);
    }

    @Override
    public RequestResponse toResponse(Requests requests) {
        return requestMapper.toResponse(requests);
    }

    private Requests salva(Requests requests){
        return requestsRepository.save(requests);
    }

    private Requests findbyId(Long id){
        return requestsRepository.findById(id).stream().findFirst().orElseThrow(() -> new IllegalRequestException("Requisição não encontrada!"));
    }

    
    private List<Requests> listaRequicicoes() {
       return requestsRepository.findAll();
    }


}
