package com.tcscontrol.control_backend.pessoa.user.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tcscontrol.control_backend.contacts.model.Contacts;
import com.tcscontrol.control_backend.pessoa.user.UserNegocio;
import com.tcscontrol.control_backend.pessoa.user.impl.mapper.UserMapper;
import com.tcscontrol.control_backend.pessoa.user.model.UserRepository;
import com.tcscontrol.control_backend.pessoa.user.model.dto.UserCreateDTO;
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
    public UserCreateDTO create(@Valid @NotNull UserCreateDTO userCreateDto) {
        return userMapper.toCreateDto(userRepository.save(userMapper.toCreateEntity(userCreateDto)));
    }

    @Override
    public UserCreateDTO update(Long id, @Valid @NotNull UserCreateDTO userCreateDto) {
        return userRepository.findById(id)
                .map(recordFound -> {
                    User user = userMapper.toCreateEntity(userCreateDto);
                    recordFound.setNmUsuario(userCreateDto.nmUsuario());
                    recordFound.setFtFoto(userCreateDto.ftFoto());
                    recordFound.setTypeUser(userMapper.convertTypeUserValue(userCreateDto.typeUser()));
                    recordFound.setStatus(userMapper.convertStatusValue(userCreateDto.flStatus()));
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
        Optional<User> user = userRepository.findByNrMatricula(nrMatricula);
        if (user.isPresent()) {
            RefreshToken rt = refreshTokenRepository.findByUser(user.get());

            if (rt != null) {
                refreshTokenRepository.deleteById(rt.getId());
            }
            userRepository.deleteById(user.get().getIdUser());
        }
    }

    @Override
    public void register(UserSenhaDTO user) {
        UserSenhaDTO userD = user;      
        String email = userD.contacts().get(0).dsContato();
        String emailText = "Sua senha de cadastro: " + UtilControl.gerarSenha(8);
        emailService.sendRegistrationEmail(email, "Bem-vindo!", emailText);
        userMapper.toCreateDto(userRepository.save(userMapper.toRegisterEntity(user)));
    }

    private Optional<User> pesquisarUserMatricula(String matricula) {
        return userRepository.findByNrMatricula(UtilCast.toInteger(matricula));
    }

    private User pesquisarUserDocumento(String documento) {
        return userRepository.findByNrCpf(documento);
    }

     private Optional<User> pesquisarUserPorContato(String dsContato) {
         Contacts contato = contactsRepository.findByDsContato(dsContato);
         if (contato != null) {
             return userRepository.findById(contato.getPessoa().getIdPessoa());
         }
         return null;
     }
    
     @Override
     public User login(String login) {
         User userLogin = pesquisarUserDocumento(login);
         if(UtilObjeto.isEmpty(userLogin)){
             userLogin = pesquisarUserPorContato(login).isPresent() ? pesquisarUserPorContato(login).get() : null ;
             if(UtilObjeto.isEmpty(userLogin)){
             userLogin = pesquisarUserMatricula(login).get();
             }
         }
         return userLogin;
     }

    @Override
    public UserDetails userLogin(String login) {
        return (UserDetails) login(login);
    }




}
