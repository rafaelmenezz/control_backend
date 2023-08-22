package com.tcscontrol.control_backend.user.impl.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.contacts.model.Contacts;
import com.tcscontrol.control_backend.contacts.model.ContactsDTO;
import com.tcscontrol.control_backend.user.model.User;
import com.tcscontrol.control_backend.user.model.UserDTO;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.enuns.TypeContacts;
import com.tcscontrol.control_backend.enuns.TypeUser;

@Component
public class UserMapper {
    
    public UserDTO toDto(User user){
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

        return new UserDTO(user.getIdUser(),
         user.getNmUsuario(),
         user.getNrMatricula(), 
         user.getNrCpf(), 
         user.getFtFoto(),
         contacts, 
         user.getFlStatus().getValue(),
         user.getTypeUser().getValue());
    }

    public User toEntity(UserDTO userDTO){
        if(userDTO == null){
            return null;
        }
        User user = new User();
        if (userDTO.id() != null) {
            user.setIdUser(userDTO.id());
        }
        user.setNmUsuario(userDTO.nmUsuario());
        user.setNrMatricula(userDTO.nrMatricula());
        user.setNrCpf(userDTO.nrCpf());
        user.setFtFoto(userDTO.ftFoto());
        user.setTypeUser(convertTypeUserValue(userDTO.typeUser()));
        user.setFlStatus(convertStatusValue(userDTO.flStatus()));

        List<Contacts> contacts = userDTO.contacts().stream()
        .map(contactsDTO -> {
            var contact = new Contacts();
            contact.setIdContacts(contactsDTO.idContacts());
            contact.setDsContato(contactsDTO.dsContato());
            contact.setTypeContacts(convertTypeContactsValue(contactsDTO.typeContacts()));
            contact.setUser(user);
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
}
