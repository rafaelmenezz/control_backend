package com.tcscontrol.control_backend.pessoa.fornecedor;

import java.util.List;

import com.tcscontrol.control_backend.contacts.model.ContactsDTO;

public record FornecedorDTO(
    Long id, 
    String nmNome,
    String nrCnpj,
    String flStatus,
    List<ContactsDTO> contacts
) {}
