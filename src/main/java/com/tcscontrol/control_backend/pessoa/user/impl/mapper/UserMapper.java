package com.tcscontrol.control_backend.pessoa.user.impl.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tcscontrol.control_backend.pessoa.user.model.dto.*;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.contacts.model.Contacts;
import com.tcscontrol.control_backend.contacts.model.ContactsDTO;
import com.tcscontrol.control_backend.enuns.DocumentoType;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.enuns.TypeContacts;
import com.tcscontrol.control_backend.enuns.TypeUser;

@Component
@AllArgsConstructor
public class UserMapper {

    public UserCreateDTO toCreateDto(User user) {
        if (user == null) {
            return null;
        }
        List<ContactsDTO> contacts = user.getContacts()
                .stream()
                .map(contact -> new ContactsDTO(
                        contact.getIdContacts(),
                        contact.getTypeContacts().getValue(),
                        contact.getDsContato()))
                .collect(Collectors.toList());

        return new UserCreateDTO(
                user.getId(),
                user.getNmName(),
                user.getNrMatricula(),
                user.getNrCpf(),
                user.getFtFoto(),
                contacts,
                user.getTpStatus().getValue(),
                user.getTypeUser().getValue());
    }

    public User toCreateEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        if (userDTO.id() != null) {
            user.setId(userDTO.id());
        }
        user.setNmName(userDTO.nmUsuario());
        user.setTypeDocumento(convertDocumentoValue(userDTO.documentoType()));
        user.setNrMatricula(userDTO.nrMatricula());
        user.setNrCpf(userDTO.nrCpf());
        user.setNmSenha(userDTO.nmSenha());
        user.setFtFoto(userDTO.ftFoto());
        user.setTypeUser(convertTypeUserValue(userDTO.typeUser()));
        user.setTpStatus(Status.ACTIVE);

        List<Contacts> contacts = userDTO.contacts().stream()
                .map(contactsDTO -> {
                    var contact = new Contacts();
                    contact.setIdContacts(contactsDTO.idContacts());
                    contact.setDsContato(contactsDTO.dsContato());
                    contact.setTypeContacts(convertTypeContactsValue(contactsDTO.typeContacts()));
                    contact.setPessoa(user);
                    return contact;
                }).collect(Collectors.toList());
        user.setContacts(contacts);

        return user;
    }

    public User toCreateEntity(UserCreateDTO userCreateDTO) {
        if (userCreateDTO == null) {
            return null;
        }
        User user = new User();
        if (userCreateDTO.id() != null) {
            user.setId(userCreateDTO.id());
        }
        user.setNmName(userCreateDTO.nmUsuario());
        user.setNrMatricula(userCreateDTO.nrMatricula());
        user.setNrCpf(userCreateDTO.nrCpf());
        user.setFtFoto(userCreateDTO.ftFoto());
        user.setTypeUser(convertTypeUserValue(userCreateDTO.typeUser()));
        user.setTpStatus(convertStatusValue(userCreateDTO.flStatus()));

        List<Contacts> contacts = userCreateDTO.contacts().stream()
                .map(contactsDTO -> {
                    var contact = new Contacts();
                    contact.setIdContacts(contactsDTO.idContacts());
                    contact.setDsContato(contactsDTO.dsContato());
                    contact.setTypeContacts(convertTypeContactsValue(contactsDTO.typeContacts()));
                    contact.setPessoa(user);
                    return contact;
                }).collect(Collectors.toList());
        user.setContacts(contacts);

        return user;
    }

    public User toRegisterEntity(UserSenhaDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        if (userDTO.id() != null) {
            user.setId(userDTO.id());
        }
        user.setNmName(userDTO.nmUsuario());
        user.setNrMatricula(userDTO.nrMatricula());
        user.setNmSenha(userDTO.nmSenha());
        user.setNrCpf(userDTO.nrCpf());
        user.setFtFoto(userDTO.ftFoto());
        user.setTypeUser(convertTypeUserValue(userDTO.typeUser()));
        user.setTpStatus(convertStatusValue(userDTO.flStatus()));

        List<Contacts> contacts = userDTO.contacts().stream()
                .map(contactsDTO -> {
                    var contact = new Contacts();
                    contact.setIdContacts(contactsDTO.idContacts());
                    contact.setDsContato(contactsDTO.dsContato());
                    contact.setTypeContacts(convertTypeContactsValue(contactsDTO.typeContacts()));
                    contact.setPessoa(user);
                    return contact;
                }).collect(Collectors.toList());
        user.setContacts(contacts);

        return user;
    }

    public TypeUser convertTypeUserValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Admin" -> TypeUser.ADMIN;
            case "Gestor" -> TypeUser.GESTOR;
            case "Peão" -> TypeUser.PEAO;
            default -> throw new IllegalArgumentException("Tipo de Usuário inválido.");
        };
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

    public DocumentoType convertDocumentoValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "CPF" -> DocumentoType.CPF;
            case "CNPJ" -> DocumentoType.CNPJ;
            default -> throw new IllegalArgumentException("Documento do usuário inválido.");
        };
    }
}
