package com.tcscontrol.control_backend.usuario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    

    @GetMapping
    public String hello(){
        return "usuario";
    }
}
