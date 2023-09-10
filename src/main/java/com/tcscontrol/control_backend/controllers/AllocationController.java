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

import com.tcscontrol.control_backend.allocation.AllocationService;
import com.tcscontrol.control_backend.allocation.model.dto.AllocationDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/alocacao")
@AllArgsConstructor
public class AllocationController {

      private final AllocationService allocationService;

      @GetMapping
      public List<AllocationDTO> list() {
            return allocationService.list();
      }

      @GetMapping("/{id}")
      public AllocationDTO findById(@PathVariable Long id) {
            return allocationService.findById(id);
      }

      @PostMapping
      @ResponseStatus(code = HttpStatus.CREATED)
      public AllocationDTO create(@RequestBody @Valid AllocationDTO allocationDTO) {
            return allocationService.create(allocationDTO);
      }

      @PutMapping("/{id}")
      public AllocationDTO update(@PathVariable Long id, @RequestBody AllocationDTO allocationDTO) {
            return allocationService.update(id, allocationDTO);
      }

}
