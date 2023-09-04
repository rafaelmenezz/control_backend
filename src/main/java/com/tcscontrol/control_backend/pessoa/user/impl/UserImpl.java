package com.tcscontrol.control_backend.pessoa.user.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tcscontrol.control_backend.contacts.model.Contacts;
import com.tcscontrol.control_backend.contacts.model.ContactsDTO;
import com.tcscontrol.control_backend.enuns.TypeContacts;
import com.tcscontrol.control_backend.pessoa.user.UserNegocio;
import com.tcscontrol.control_backend.pessoa.user.impl.mapper.UserMapper;
import com.tcscontrol.control_backend.pessoa.user.model.UserRepository;
import com.tcscontrol.control_backend.pessoa.user.model.dto.UserCreateDTO;
import com.tcscontrol.control_backend.pessoa.user.model.dto.UserDTO;
import com.tcscontrol.control_backend.pessoa.user.model.dto.UserSenhaDTO;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.auth.model.RefreshToken;
import com.tcscontrol.control_backend.auth.repository.RefreshTokenRepository;
import com.tcscontrol.control_backend.contacts.ContactsRepository;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.utilitarios.EmailService;
import com.tcscontrol.control_backend.utilitarios.UtilCast;
import com.tcscontrol.control_backend.utilitarios.UtilControl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;


@Component(value = "userNegocio")
@AllArgsConstructor
public class UserImpl implements UserNegocio {

    UserRepository userRepository;
    RefreshTokenRepository refreshTokenRepository;
    ContactsRepository contactsRepository;
    UserMapper userMapper;
    EmailService emailService;
    

    public List<UserCreateDTO> list() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toCreateDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserCreateDTO findById(@NotNull @Positive Long id) {
        return userRepository.findById(id)
                .map(userMapper::toCreateDto)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Override
    public UserCreateDTO create(@Valid @NotNull UserDTO userDTO, String password) { 
        String email = obtemEmailDTO(userDTO.contacts());
        emailService.enviarEmail(email, password);
        return userMapper.toCreateDto(userRepository.save(userMapper.toCreateEntity(userDTO)));
    }

    @Override
    public UserCreateDTO update(Long id, @Valid @NotNull UserCreateDTO userCreateDto) {
        return userRepository.findById(id)
                .map(recordFound -> {
                    User user = userMapper.toCreateEntity(userCreateDto);
                    recordFound.setNmName(userCreateDto.nmUsuario());
                    recordFound.setFtFoto(userCreateDto.ftFoto());
                    recordFound.setTypeUser(userMapper.convertTypeUserValue(userCreateDto.typeUser()));
                    recordFound.setTpStatus(userMapper.convertStatusValue(userCreateDto.flStatus()));
                    recordFound.getContacts().clear();
                    user.getContacts().forEach(recordFound.getContacts()::add);
                    return userMapper.toCreateDto(userRepository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Override
    public void delete(@NotNull @Positive Long id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
    }

    @Override
    public void deleteCascade(Integer nrMatricula) {
        User user = userRepository.findByNrMatricula(nrMatricula);
        if (!UtilObjeto.isEmpty(user)) {
            RefreshToken rt = refreshTokenRepository.findByUser(user);

            if (rt != null) {
                refreshTokenRepository.deleteById(rt.getId());
            }
            userRepository.deleteById(user.getId());
        }
    }

    @Override
    public void register(UserSenhaDTO user) {
        UserSenhaDTO userD = user;      
        String email = userD.contacts().get(0).dsContato();
        emailService.enviarEmail(email, UtilControl.gerarSenha(8));
        userMapper.toCreateDto(userRepository.save(userMapper.toRegisterEntity(user)));
    }

    private User pesquisarUserMatricula(String matricula) {
        if(!UtilObjeto.isNumero(matricula)){
            return null;
        }
        return userRepository.findByNrMatricula(UtilCast.toInteger(matricula));
    }

    private User pesquisarUserDocumento(String documento) {
        return userRepository.findByNrCpf(documento);
    }

     private Optional<User> pesquisarUserPorContato(String dsContato) {
         Contacts contato = contactsRepository.findByDsContato(dsContato);
         if (contato != null) {
             return userRepository.findById(contato.getPessoa().getId());
         }
         return null;
     }
    
     @Override
     public User login(String login) {
         User userLogin = pesquisarUserDocumento(login);
         if(UtilObjeto.isEmpty(userLogin)){
             userLogin = pesquisarUserMatricula(login);
             if(UtilObjeto.isEmpty(userLogin)){
             userLogin =  pesquisarUserPorContato(login).get() ;
             }
         }
         return userLogin;
     }

    @Override
    public UserDetails userLogin(String login) {
        return (UserDetails) userRepository.validarLogin(UtilCast.toInteger(login));
    }

    private String obtemEmailDTO (List<ContactsDTO> dto){
        List<ContactsDTO> contatos = dto;
        String email = "";
        for (ContactsDTO obj : contatos) {
            if(TypeContacts.EMAIL.getValue().equals(obj.typeContacts())){
                email = obj.dsContato();
            }
        }
        return email;
    }

    @Override
    public User obtemUserMatricula(String matricula) {
        return pesquisarUserMatricula(matricula);
    }


}
