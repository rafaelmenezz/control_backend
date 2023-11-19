package com.tcscontrol.control_backend.pessoa.fornecedor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.contacts.model.Contacts;
import com.tcscontrol.control_backend.contacts.model.ContactsDTO;
import com.tcscontrol.control_backend.enuns.DocumentoType;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.enuns.TypeContacts;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FornecedorMapper {

    public FornecedorDTO toDTO(Fornecedor fornecedor) {

        if (fornecedor == null) {
            return null;
        }
        List<ContactsDTO> contacts = fornecedor.getContacts()
                .stream()
                .map(contact -> new ContactsDTO(
                        contact.getId(),
                        contact.getTypeContacts().getValue(),
                        contact.getDsContato()))
                .collect(Collectors.toList());

        return new FornecedorDTO(
                fornecedor.getId(),
                fornecedor.getNmName(),
                fornecedor.getNrCnpj(),
                fornecedor.getTpStatus().getValue(),
                contacts);

    }

    public Fornecedor toEntity(FornecedorDTO fornecedorDTO) {
        if (fornecedorDTO == null) {
            return null;
        }
        Fornecedor fornecedor = new Fornecedor();
        if (fornecedorDTO.id() != null) {
            fornecedor.setId(fornecedorDTO.id());
        }

        fornecedor.setNmName(fornecedorDTO.nmNome());
        fornecedor.setNrCnpj(fornecedorDTO.nrCnpj());
        fornecedor.setTpStatus(convertStatusValue(fornecedorDTO.flStatus()));
        fornecedor.setTypeDocumento(DocumentoType.CNPJ);

        List<Contacts> contatos = fornecedorDTO.contacts().stream()
        .map(contactsDTO-> {
                    var contact = new Contacts();
                    contact.setId(contactsDTO.idContacts());
                    contact.setDsContato(contactsDTO.dsContato());
                    contact.setTypeContacts(convertTypeContactsValue(contactsDTO.typeContacts()));
                    contact.setPessoa(fornecedor);
                    return contact;
                }).collect(Collectors.toList());
                fornecedor.setContacts(contatos);

                return fornecedor;
    }

    public TypeContacts convertTypeContactsValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Telefone" -> TypeContacts.TELEFONE;
            case "Celular" -> TypeContacts.CELULAR;
            case "E-mail" -> TypeContacts.EMAIL;
            case "Whatsapp" -> TypeContacts.WHATSAPP;
            case "Instagran" -> TypeContacts.INSTAGRAN;
            default -> throw new IllegalArgumentException("Tipo de Usuário inválido.");
        };
    }

    public Status convertStatusValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Ativo" -> Status.ACTIVE;
            case "Inativo" -> Status.INACTIVE;
            default -> throw new IllegalArgumentException("Status do usuário inválido.");
        };
    }
}
