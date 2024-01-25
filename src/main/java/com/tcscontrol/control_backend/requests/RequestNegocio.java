package com.tcscontrol.control_backend.requests;

import java.util.List;
import java.util.Map;

import com.tcscontrol.control_backend.requests.model.dto.RequestResponse;
import com.tcscontrol.control_backend.requests.model.entity.Requests;

public interface RequestNegocio extends RequestService {

    Requests salvaRequests(Requests requests);

    Requests pesquisaRequests(Long id);

    RequestResponse toResponse(Requests requests);

    List<Map<String, Object>> obterPatrimoniosAVencer();

    List<Map<String, Object>> obterPatrimoniosVencidos();

    Map<String, Object> obterPatrimonioEnvioEmailAdmin();
      
}
