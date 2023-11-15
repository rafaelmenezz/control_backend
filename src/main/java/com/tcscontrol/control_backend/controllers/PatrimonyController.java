package com.tcscontrol.control_backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcscontrol.control_backend.patrimony.PatrimonyService;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyDTO;
import com.tcscontrol.control_backend.patrimony.model.dto.PatrimonyResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/patrimony")
@AllArgsConstructor
public class PatrimonyController {

    private final PatrimonyService patrimonyService;

    @GetMapping
    public List<PatrimonyResponse> list() {
        return patrimonyService.list();
    }

    @GetMapping("/{id}")
    public PatrimonyResponse findById(@PathVariable Long id) {
        return patrimonyService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PatrimonyResponse create(@RequestBody @Valid PatrimonyDTO data) {
        return patrimonyService.create(data);
    }

    @PutMapping("/{id}")
    public PatrimonyResponse update(@PathVariable Long id, @RequestBody PatrimonyDTO data) {
        return patrimonyService.update(id, data);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        patrimonyService.delete(id);
    }

    @GetMapping("search")
    public ResponseEntity<List<PatrimonyResponse>>  search(
        @RequestParam(name = "nmPatrimonio", required = false) String nmPatrimonio,
        @RequestParam(name = "nrSerie", required = false) String nrSerie,
        @RequestParam(name = "dsPatrimonio", required = false) String dsPatrimonio,
        @RequestParam(name = "nrCnpj", required = false) String nrCnpj,
        @RequestParam(name = "nmFornecedor", required = false) String nmFornecedor,
        @RequestParam(name = "dtAquisicao", required = false) String dtAquisicao
    ){
        List<PatrimonyResponse> list = patrimonyService.search(nmPatrimonio, nrSerie, dsPatrimonio, nrCnpj, nmFornecedor, dtAquisicao);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(list);
        }
    }

    @GetMapping("/allocation")
    public ResponseEntity<List<PatrimonyResponse>> findPatrimoniesToAllocation(
         @RequestParam(name = "nmPatrimonio", required = false) String nmPatrimonio
        ){ 
        List<PatrimonyResponse> list = patrimonyService.listPatrimoniesFixOrNotFix(nmPatrimonio, Boolean.TRUE);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok().body(list);
        }
        
    }
    @GetMapping("/construction")
    public ResponseEntity<List<PatrimonyResponse>> findPatrimoniesToConstruction(
         @RequestParam(name = "nmPatrimonio", required = false) String nmPatrimonio
        ){ 
        List<PatrimonyResponse> list = patrimonyService.listPatrimoniesFixOrNotFix(nmPatrimonio, Boolean.FALSE);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok().body(list);
        }
        
    }

    @PostMapping("/baixa-patrimonio")
    public PatrimonyDTO addLostThief(@RequestBody PatrimonyDTO patrimonyDTO) {
        
        return null;
    }
    
}
