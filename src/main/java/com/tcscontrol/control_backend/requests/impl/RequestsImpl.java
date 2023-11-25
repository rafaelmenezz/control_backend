package com.tcscontrol.control_backend.requests.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.tcscontrol.control_backend.exception.IllegalRequestException;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;
import com.tcscontrol.control_backend.request_patrimony.model.entity.RequestPatrimony;
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

    @Override
    public List<Map<String, Object>> obterPatrimoniosAVencer() {
        Date dataAtual = java.sql.Date.valueOf(LocalDate.now());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataAtual);
        int diasASomar = 1;
        calendar.add(Calendar.DAY_OF_MONTH, diasASomar);
        Date novaData = calendar.getTime();

        List<Map<String, Object>> retorno = new ArrayList<>();
        List<User> idsUsuario = requestsRepository.getUsuariosPatrimoniosVencer(novaData);
        List<RequestPatrimony> rp = requestsRepository.getPatrimoniosVencer(novaData);
        Map<String, Object> dados = new HashMap<>();
        for (User id : idsUsuario) {
            dados.put("USUARIO", id);
            dados.put("PATRIMONIOS", montaDadosParaEnvioEmail(id.getId(), rp));
            retorno.add(dados);
        }
        return retorno;
    }

    @Override
    public List<Map<String, Object>> obterPatrimoniosVencidos() {

        Date dataAtual = java.sql.Date.valueOf(LocalDate.now());
        requestsRepository.getPatrimoniosVencidos(dataAtual);

        List<Map<String, Object>> retorno = new ArrayList<>();
        List<User> idsUsuario = requestsRepository.getUsuariosPatrimoniosVencidos(dataAtual);
        List<RequestPatrimony> rp = requestsRepository.getPatrimoniosVencidos(dataAtual);
        Map<String, Object> dados = new HashMap<>();
        for (User id : idsUsuario) {
            dados.put("USUARIO", id);
            dados.put("PATRIMONIOS", montaDadosParaEnvioEmail(id.getId(), rp));
            retorno.add(dados);
        }
       
        return null;
    }

    public Map<String, Object> obterPatrimonioEnvioEmailAdmin(){
        Date dataAtual = java.sql.Date.valueOf(LocalDate.now());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataAtual);
        int diasASomar = 1;
        calendar.add(Calendar.DAY_OF_MONTH, diasASomar);
        Date novaData = calendar.getTime();
        List<RequestPatrimony> vencidos = requestsRepository.getPatrimoniosVencidos(dataAtual);
        List<RequestPatrimony> vencer = requestsRepository.getPatrimoniosVencer(novaData);

        Map<String, Object> dados = new HashMap<>();

        dados.put("VENCIDOS", vencidos);
        dados.put("VENCER", vencer);

        return dados;
    }

    private List<RequestPatrimony> montaDadosParaEnvioEmail(Long id, List<RequestPatrimony> rp){
        List<RequestPatrimony> rpRetorno = new ArrayList<>();
        for (RequestPatrimony requestPatrimony : rp) {
            if (id.equals(requestPatrimony.getRequests().getConstruction().getUser().getId()))
                rpRetorno.add(requestPatrimony);
        }

        return rpRetorno;
    }
    



}
