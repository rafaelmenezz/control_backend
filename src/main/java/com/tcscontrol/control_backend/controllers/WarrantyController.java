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

import com.tcscontrol.control_backend.warranty.WarrantyService;
import com.tcscontrol.control_backend.warranty.model.dto.WarrantyDTO;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/garantia")
@AllArgsConstructor
public class WarrantyController {
      
      private final WarrantyService warrantyService;


      @GetMapping
      public List<WarrantyDTO> list(){
            return warrantyService.list();
      }

      @GetMapping("{id}")
      public WarrantyDTO findById(@PathVariable Long id){
            return warrantyService.findById(id);
      }

      @PostMapping
      @ResponseStatus(code = HttpStatus.CREATED)
      public WarrantyDTO create(@RequestBody WarrantyDTO warrantyDTO){
            return warrantyService.create(warrantyDTO);
      }

      @PutMapping("{id}")
      @ResponseStatus(code = HttpStatus.NO_CONTENT)
      public WarrantyDTO update(@PathVariable Long id, @RequestBody WarrantyDTO warrantyDTO){
            return warrantyService.update(id, warrantyDTO);
      }


}
