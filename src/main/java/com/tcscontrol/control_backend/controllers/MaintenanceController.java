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

import com.tcscontrol.control_backend.maintenance.MaintenanceService;
import com.tcscontrol.control_backend.maintenance.model.dto.MaintenanceDTO;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/manutencao")
@AllArgsConstructor
public class MaintenanceController {

      private final MaintenanceService maintenanceService;

      @GetMapping
      public List<MaintenanceDTO> list() {
            return maintenanceService.list();
      }

      @GetMapping("{id}")
      public MaintenanceDTO findById(@PathVariable Long id) {
            return maintenanceService.findById(id);
      }

      @PostMapping
      @ResponseStatus(code = HttpStatus.CREATED)
      public MaintenanceDTO create(@RequestBody MaintenanceDTO maintenanceDTO) {
            return maintenanceService.create(maintenanceDTO);
      }

      @PutMapping("{id}")
      @ResponseStatus(code = HttpStatus.NO_CONTENT)
      public MaintenanceDTO update(@PathVariable Long id, @RequestBody MaintenanceDTO maintenanceDTO) {
            return maintenanceService.update(id, maintenanceDTO);
      }
}
