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

import com.tcscontrol.control_backend.patrimonyConstruction.PatrimonyContructionService;
import com.tcscontrol.control_backend.patrimonyConstruction.model.dto.PatrimonyContructionDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/alocacao")
@AllArgsConstructor
public class PatrimonyContructionController {

      private final PatrimonyContructionService allocationService;

      @GetMapping
      public List<PatrimonyContructionDTO> list() {
            return allocationService.list();
      }

      @GetMapping("/{id}")
      public PatrimonyContructionDTO findById(@PathVariable Long id) {
            return allocationService.findById(id);
      }

      @PostMapping
      @ResponseStatus(code = HttpStatus.CREATED)
      public PatrimonyContructionDTO create(@RequestBody @Valid PatrimonyContructionDTO allocationDTO) {
            return allocationService.create(allocationDTO);
      }

      @PutMapping("/{id}")
      public PatrimonyContructionDTO update(@PathVariable Long id, @RequestBody PatrimonyContructionDTO allocationDTO) {
            return allocationService.update(id, allocationDTO);
      }

}
