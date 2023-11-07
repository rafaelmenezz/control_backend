package com.tcscontrol.control_backend.request_patrimony;


import org.springframework.stereotype.Service;

import com.tcscontrol.control_backend.requests.model.dto.RequestResponse;
import com.tcscontrol.control_backend.requests.model.dto.RequestsDTO;

@Service
public interface RequestPatrimonyService {
    RequestResponse addNewRequest(RequestsDTO requestsDTO);

    RequestResponse toRemove(RequestsDTO requestsDTO);

    RequestResponse giveBackPatrimony(RequestsDTO requestsDTO);

    RequestResponse cancel(RequestsDTO requestsDTO);

}
