package com.tcscontrol.control_backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/patrimonio")
@AllArgsConstructor
public class PatrimonyController {

    private final PatrimonyService patrimonyService;

    @GetMapping
    public List<PatrimonyDTO> list() {
        return patrimonyService.list();
    }

    @GetMapping("/{id}")
    public PatrimonyDTO findById(@PathVariable Long id) {
        return patrimonyService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PatrimonyDTO create(@RequestBody @Valid PatrimonyDTO patrimonyDTO) {
        return patrimonyService.create(patrimonyDTO);
    }

    @PutMapping("/{id}")
    public PatrimonyDTO update(@PathVariable Long id, @RequestBody PatrimonyDTO patrimonyDTO) {
        return patrimonyService.update(id, patrimonyDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        patrimonyService.delete(id);
    }

    @GetMapping("search")
    public List<PatrimonyDTO> search(
        @RequestParam(name = "nmPatrimonio", required = false) String nmPatrimonio,
        @RequestParam(name = "nrSerie", required = false) String nrSerie,
        @RequestParam(name = "dsPatrimonio", required = false) String dsPatrimonio,
        @RequestParam(name = "nrCnpj", required = false) String nrCnpj,
        @RequestParam(name = "nmFornecedor", required = false) String nmFornecedor,
        @RequestParam(name = "dtAquisicao", required = false) String dtAquisicao
    ){
        return patrimonyService.search(nmPatrimonio, nrSerie, dsPatrimonio, nrCnpj, nmFornecedor, dtAquisicao);
    }
}
