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

import com.tcscontrol.control_backend.constructions.ConstructionService;
import com.tcscontrol.control_backend.constructions.model.dto.ConstructionDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/obra")
public class ConstructionController {

      private final ConstructionService constructionService;

      @GetMapping
      public List<ConstructionDTO> list() {
            return constructionService.list();
      }

      @GetMapping("/{id}")
      public ConstructionDTO findById(@PathVariable Long id) {
            return constructionService.findById(id);
      }

      @PostMapping
      @ResponseStatus(code = HttpStatus.CREATED)
      public ConstructionDTO create(@RequestBody @Valid ConstructionDTO constructionDTO) {
            return constructionService.create(constructionDTO);
      }

      @PatchMapping("/{id}")
      public ConstructionDTO update(@PathVariable Long id, @RequestBody ConstructionDTO constructionDTO) {
            return constructionService.update(id, constructionDTO);
      }

      @DeleteMapping("/{id}")
      @ResponseStatus(code = HttpStatus.NO_CONTENT)
      public void delete(@PathVariable Long id) {
            constructionService.delete(id);
      }

}
