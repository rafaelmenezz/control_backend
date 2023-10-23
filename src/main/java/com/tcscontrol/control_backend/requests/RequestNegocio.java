package com.tcscontrol.control_backend.requests;

import com.tcscontrol.control_backend.requests.model.dto.RequestResponse;
import com.tcscontrol.control_backend.requests.model.entity.Requests;

public interface RequestNegocio extends RequestService {

    Requests salvaRequests(Requests requests);

    Requests pesquisaRequests(Long id);

    RequestResponse toResponse(Requests requests);
      
}
