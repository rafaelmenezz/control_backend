package com.tcscontrol.control_backend.user.model.dto;

import java.util.List;

import com.tcscontrol.control_backend.contacts.model.ContactsDTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(
    Long id,
    @NotBlank @NotNull String nmUsuario,
    Integer nrMatricula, 
    @NotBlank @NotNull String nrCpf,
    Byte[] ftFoto, 
    @NotNull List<ContactsDTO> contacts, 
    String flStatus,
    @NotNull String typeUser

) {

}
