package com.tcscontrol.control_backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcscontrol.control_backend.pessoa.fornecedor.FornecedorDTO;
import com.tcscontrol.control_backend.pessoa.fornecedor.FornecedorService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@Validated
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/fornecedor")
@AllArgsConstructor
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @GetMapping
    public List<FornecedorDTO> list() {
        return fornecedorService.list();
    }

    @GetMapping("/{id}")
    public FornecedorDTO findById(@PathVariable @NotNull @Positive Long id) {
        return fornecedorService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public FornecedorDTO create(@RequestBody @Valid FornecedorDTO fornecedorDTO) {
        return fornecedorService.create(fornecedorDTO);
    }

    @PatchMapping("/{id}")
    public FornecedorDTO update(@PathVariable Long id, @RequestBody @Valid FornecedorDTO fornecedorDTO) {
        return fornecedorService.update(id, fornecedorDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id) {
        fornecedorService.delete(id);
    }

}
