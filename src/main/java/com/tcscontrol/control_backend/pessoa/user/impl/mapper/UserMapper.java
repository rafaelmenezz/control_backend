package com.tcscontrol.control_backend.pessoa.user.impl.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tcscontrol.control_backend.pessoa.user.model.dto.*;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;
import com.tcscontrol.control_backend.utilitarios.UtilControl;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.contacts.model.Contacts;
import com.tcscontrol.control_backend.contacts.model.ContactsDTO;
import com.tcscontrol.control_backend.enuns.Status;



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
                user.getTypeUser().getValue(),
                user.getPrimeiroAcesso());
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
        user.setTypeDocumento(UtilControl.convertDocumentoValue(userDTO.documentoType()));
        user.setNrMatricula(userDTO.nrMatricula());
        user.setNrCpf(userDTO.nrCpf());
        user.setNmSenha(userDTO.nmSenha());
        user.setFtFoto(userDTO.ftFoto());
        user.setTypeUser(UtilControl.convertTypeUserValue(userDTO.typeUser()));
        user.setTpStatus(Status.ACTIVE);

        List<Contacts> contacts = userDTO.contacts().stream()
                .map(contactsDTO -> {
                    var contact = new Contacts();
                    contact.setIdContacts(contactsDTO.idContacts());
                    contact.setDsContato(contactsDTO.dsContato());
                    contact.setTypeContacts(UtilControl.convertTypeContactsValue(contactsDTO.typeContacts()));
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
        user.setTypeUser(UtilControl.convertTypeUserValue(userCreateDTO.typeUser()));
        user.setTpStatus(UtilControl.convertStatusValue(userCreateDTO.flStatus()));

        List<Contacts> contacts = userCreateDTO.contacts().stream()
                .map(contactsDTO -> {
                    var contact = new Contacts();
                    contact.setIdContacts(contactsDTO.idContacts());
                    contact.setDsContato(contactsDTO.dsContato());
                    contact.setTypeContacts(UtilControl.convertTypeContactsValue(contactsDTO.typeContacts()));
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
        user.setTypeUser(UtilControl.convertTypeUserValue(userDTO.typeUser()));
        user.setTpStatus(UtilControl.convertStatusValue(userDTO.flStatus()));
        user.setPrimeiroAcesso(false);

        List<Contacts> contacts = userDTO.contacts().stream()
                .map(contactsDTO -> {
                    var contact = new Contacts();
                    contact.setIdContacts(contactsDTO.idContacts());
                    contact.setDsContato(contactsDTO.dsContato());
                    contact.setTypeContacts(UtilControl.convertTypeContactsValue(contactsDTO.typeContacts()));
                    contact.setPessoa(user);
                    return contact;
                }).collect(Collectors.toList());
        user.setContacts(contacts);

        return user;
    }

    public UserResponse toResponse(User user) {
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

        return new UserResponse(
                user.getId(),
                user.getNmName(),
                user.getNrMatricula(),
                user.getNrCpf(),
                user.getFtFoto(),
                contacts);
    }

    public User toEntity(UserResponse userResponse){
        if (UtilObjeto.isEmpty(userResponse)) {
            return null;
        }
        User user = new User();
        if (UtilObjeto.isNotEmpty(userResponse.id())) {
            user.setId(userResponse.id());
        }

        user.setNmName(userResponse.nmUsuario());
        user.setNrMatricula(userResponse.nrMatricula());
        user.setNrCpf(userResponse.nrCpf());
        user.setFtFoto(userResponse.ftFoto());

        List<Contacts> contacts = userResponse.contacts().stream()
                .map(contactsDTO -> {
                    var contact = new Contacts();
                    contact.setIdContacts(contactsDTO.idContacts());
                    contact.setDsContato(contactsDTO.dsContato());
                    contact.setTypeContacts(UtilControl.convertTypeContactsValue(contactsDTO.typeContacts()));
                    contact.setPessoa(user);
                    return contact;
                }).collect(Collectors.toList());
        user.setContacts(contacts);

        return user;
    }

}
