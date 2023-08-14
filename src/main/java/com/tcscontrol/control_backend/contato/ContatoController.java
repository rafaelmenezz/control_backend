package com.tcscontrol.control_backend.contato;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contato")
public class ContatoController {

    @GetMapping
    public String hello(){
        return "contato";
    }
}
