package com.tcscontrol.control_backend.contacts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contato")
public class ContactsController {

    @GetMapping
    public String hello(){
        return "contato";
    }
}
