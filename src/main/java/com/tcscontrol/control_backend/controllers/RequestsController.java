package com.tcscontrol.control_backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcscontrol.control_backend.request_patrimony.RequestPatrimonyService;
import com.tcscontrol.control_backend.requests.RequestService;
import com.tcscontrol.control_backend.requests.model.dto.RequestResponse;
import com.tcscontrol.control_backend.requests.model.dto.RequestsDTO;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/requests")
@AllArgsConstructor
public class RequestsController {

    private final RequestService requestService;
    private final RequestPatrimonyService requestPatrimonyService;

    @GetMapping
    public List<RequestResponse> list(){
        return requestService.list();
    }

    @GetMapping("/{id}")
    public RequestResponse findById(@PathVariable Long id){
        return requestService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public RequestResponse create(@RequestBody RequestsDTO requestsDTO){
        return requestPatrimonyService.addNewRequest(requestsDTO);
    }

    @PutMapping("/retirar")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public RequestResponse retirar(@RequestBody RequestsDTO requestsDTO){
        return requestPatrimonyService.toRemove(requestsDTO);
    } 

    @PutMapping("/devolver-patrimonio")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public RequestResponse giveBack(@RequestBody RequestsDTO requestsDTO){
        return requestPatrimonyService.giveBackPatrimony(requestsDTO);
    } 

    @PutMapping("/cancelar")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public RequestResponse cancel(@RequestBody RequestsDTO requestsDTO){
        return requestPatrimonyService.cancel(requestsDTO);
    } 



    
}
