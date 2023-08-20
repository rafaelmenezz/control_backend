package com.tcscontrol.control_backend.user.model;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.tcscontrol.control_backend.contacts.model.ContactsDTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(
    Long id,
    @NotBlank @NotNull String nmUsuario,
    Integer nrMatricula, 
    @NotBlank @Length(min = 6, max = 20) String nmSenha,
    @NotBlank @NotNull String nrCpf,
    Byte[] ftFoto, 
    @NotNull List<ContactsDTO> contacts, 
    @NotNull String typeUser

) {

}
