package com.tcscontrol.control_backend.controllers;

import java.util.List;

import com.tcscontrol.control_backend.enuns.DocumentoType;
import com.tcscontrol.control_backend.pessoa.user.UserService;
import com.tcscontrol.control_backend.pessoa.user.model.dto.UserCreateDTO;
import com.tcscontrol.control_backend.pessoa.user.model.dto.UserDTO;
import com.tcscontrol.control_backend.utilitarios.UtilControl;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    
    private final UserService userService;

    @GetMapping
    public List<UserCreateDTO> list(){
        return userService.list();
    }

    @GetMapping("/{id}")
    public UserCreateDTO findById(@PathVariable @NotNull @Positive Long id){
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserCreateDTO create(@RequestBody UserCreateDTO userCreateDto){
        String password = UtilControl.gerarSenha(8);
        UserDTO userDTO = new UserDTO(userCreateDto.id(),
        userCreateDto.nmUsuario(), 
        DocumentoType.CPF.getValue(),
        userCreateDto.nrMatricula(), 
        userCreateDto.nrCpf(), 
        new BCryptPasswordEncoder().encode(password), 
        userCreateDto.ftFoto(), 
        userCreateDto.contacts(), 
        userCreateDto.flStatus(), 
        userCreateDto.typeUser());
        return userService.create(userDTO, password);
    }

    @PatchMapping("/{id}")
    public UserCreateDTO update(@PathVariable Long id, @RequestBody @Valid UserCreateDTO userCreateDto){
        return userService.update(id, userCreateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id){
        userService.delete(id);
    }

}
