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

import com.tcscontrol.control_backend.patrimonydepartment.PatrimonyDepartmentService;
import com.tcscontrol.control_backend.patrimonydepartment.model.dto.PatrimonyDepartmentDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/alocacao")
@AllArgsConstructor
public class PatrimonyDepartmentController {

      private final PatrimonyDepartmentService allocationService;

      @GetMapping
      public List<PatrimonyDepartmentDTO> list() {
            return allocationService.list();
      }

      @GetMapping("/{id}")
      public PatrimonyDepartmentDTO findById(@PathVariable Long id) {
            return allocationService.findById(id);
      }

      @PostMapping
      @ResponseStatus(code = HttpStatus.CREATED)
      public PatrimonyDepartmentDTO create(@RequestBody @Valid PatrimonyDepartmentDTO allocationDTO) {
            return allocationService.create(allocationDTO);
      }

      @PutMapping("/{id}")
      public PatrimonyDepartmentDTO update(@PathVariable Long id, @RequestBody PatrimonyDepartmentDTO allocationDTO) {
            return allocationService.update(id, allocationDTO);
      }

}
