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
import com.tcscontrol.control_backend.allocation.model.dto.AllocationResponse;
import com.tcscontrol.control_backend.allocation_patrimony.AllocationPatrimonyService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/allocation")
@AllArgsConstructor
public class AllocationController {

      private final AllocationService allocationService;
      private final AllocationPatrimonyService allocationPatrimonyService;

      @GetMapping
      public List<AllocationResponse> list() {
            return allocationService.list();
      }

      @GetMapping("/{id}")
      public AllocationResponse findById(@PathVariable Long id) {
            return allocationService.findById(id);
      }

      @PostMapping
      @ResponseStatus(code = HttpStatus.CREATED)
      public AllocationResponse create(@RequestBody @Valid AllocationDTO allocationDTO) {
            return allocationPatrimonyService.create(allocationDTO);
      }

      @PutMapping("/{id}")
      public AllocationResponse update(@PathVariable Long id, @RequestBody AllocationDTO allocationDTO) {
            return allocationPatrimonyService.update(id, allocationDTO);
      }

}
