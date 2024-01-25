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

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/maintenance")
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

      @PutMapping("/agendar/{id}")
      @ResponseStatus(code = HttpStatus.NO_CONTENT)
      public MaintenanceDTO agendar(@PathVariable Long id, @RequestBody MaintenanceDTO maintenanceDTO) {
            return maintenanceService.toSchedule(id, maintenanceDTO);
      }

      @PutMapping("/iniciar/{id}")
      @ResponseStatus(code = HttpStatus.NO_CONTENT)
      public MaintenanceDTO iniciar(@PathVariable Long id, @RequestBody MaintenanceDTO maintenanceDTO) {
            return maintenanceService.toExecute(id, maintenanceDTO);
      }

      @PutMapping("/finalizar/{id}")
      @ResponseStatus(code = HttpStatus.NO_CONTENT)
      public MaintenanceDTO finalizar(@PathVariable Long id, @RequestBody MaintenanceDTO maintenanceDTO) {
            return maintenanceService.toFinish(id, maintenanceDTO);
      }

      @PutMapping("/cancelar/{id}")
      @ResponseStatus(code = HttpStatus.NO_CONTENT)
      public void cancelar(@PathVariable Long id, @RequestBody MaintenanceDTO maintenanceDTO) {
            maintenanceService.cancel(id, maintenanceDTO);
      }
}
