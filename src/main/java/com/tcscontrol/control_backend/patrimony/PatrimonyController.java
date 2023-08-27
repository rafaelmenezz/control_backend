package com.tcscontrol.control_backend.patrimony;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patrimonio")
public class PatrimonyController {
    
    @GetMapping
    public String hello(){
        return "Patrimonio alcione";
    }
}
