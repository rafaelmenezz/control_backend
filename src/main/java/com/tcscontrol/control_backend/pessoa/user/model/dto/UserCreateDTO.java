package com.tcscontrol.control_backend.pessoa.user.model.dto;

import java.util.List;

import com.tcscontrol.control_backend.contacts.model.ContactsDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateDTO(
    Long id,
    @NotBlank @NotNull String nmUsuario,
    String nrMatricula, 
    @NotBlank @NotNull String nrCpf,
    String ftFoto, 
    @NotNull List<ContactsDTO> contacts, 
    String flStatus,
    String typeUser
) {

}
