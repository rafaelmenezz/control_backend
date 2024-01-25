package com.tcscontrol.control_backend.requests;

import java.util.List;

import org.springframework.stereotype.Service;


import com.tcscontrol.control_backend.requests.model.dto.RequestResponse;

@Service
public interface RequestService {
      
    List<RequestResponse> list();

    RequestResponse findById(Long id);
}
