package com.tcscontrol.control_backend.pessoa.user.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tcscontrol.control_backend.contacts.model.Contacts;
import com.tcscontrol.control_backend.enuns.TypeUser;
import com.tcscontrol.control_backend.enviar_email.EmailNegocio;
import com.tcscontrol.control_backend.pessoa.user.UserNegocio;
import com.tcscontrol.control_backend.pessoa.user.impl.mapper.UserMapper;
import com.tcscontrol.control_backend.pessoa.user.model.UserRepository;
import com.tcscontrol.control_backend.pessoa.user.model.dto.RecoverPassword;
import com.tcscontrol.control_backend.pessoa.user.model.dto.ReqUpdatePassword;
import com.tcscontrol.control_backend.pessoa.user.model.dto.UserCreateDTO;
import com.tcscontrol.control_backend.pessoa.user.model.dto.UserDTO;
import com.tcscontrol.control_backend.pessoa.user.model.dto.UserSenhaDTO;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.auth.model.RefreshToken;
import com.tcscontrol.control_backend.auth.repository.RefreshTokenRepository;
import com.tcscontrol.control_backend.config.SecurityConfig;
import com.tcscontrol.control_backend.contacts.ContactsRepository;
import com.tcscontrol.control_backend.exception.IllegalRequestException;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
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
    EmailNegocio emailNegocio;
    SecurityConfig config;
    

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
        emailNegocio.enviarEmailNovoUsuario(userMapper.toCreateEntity(userDTO), password);
        return userMapper.toCreateDto(userRepository.save(userMapper.toCreateEntity(userDTO)));
    }

    @Override
    public UserCreateDTO update(Long id, @Valid @NotNull UserCreateDTO userCreateDto) {
        return userRepository.findById(id)
                .map(recordFound -> {
                    User user = userMapper.toCreateEntity(userCreateDto);
                    recordFound.setNmName(userCreateDto.nmUsuario());
                    recordFound.setFtFoto(userCreateDto.ftFoto());
                    recordFound.setTypeUser(UtilControl.convertTypeUserValue(userCreateDto.typeUser()));
                    recordFound.setTpStatus(UtilControl.convertStatusValue(userCreateDto.flStatus()));
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
    public void deleteCascade(String nrMatricula) {
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
    public void register(UserSenhaDTO userD) {;      
        User user = userMapper.toRegisterEntity(userD);
        emailNegocio.enviarEmailNovoUsuario(user, UtilControl.gerarSenha(8));
        userMapper.toCreateDto(userRepository.save(user));
    }

    private User pesquisarUserMatricula(String matricula) {
        if(!UtilObjeto.isNumero(matricula)){
            return null;
        }
        return userRepository.findByNrMatricula(matricula);
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
        return userRepository.validarLogin(login);
    }

    @Override
    public User obtemUserMatricula(String matricula) {
        return pesquisarUserMatricula(matricula);
    }

    @Override
    public void updatePassword(Long id, ReqUpdatePassword reqUpdatePassword) {
        
        if(!reqUpdatePassword.newPassword1().equals(reqUpdatePassword.newPassword2())){
            throw new IllegalRequestException("Nova senha e confirmar senha são diferentes");
        }
        User user = userRepository.findById(id).get();
        boolean senhasIguais = config.passwordEncoder().matches( reqUpdatePassword.currentPassword(), user.getNmSenha());
        if(senhasIguais){
            user.setNmSenha(config.passwordEncoder().encode(reqUpdatePassword.newPassword1()));
            user.setPrimeiroAcesso(Boolean.FALSE);
            userRepository.saveAndFlush(user);
        }else{
            throw new IllegalRequestException("Senha informada inválida!");
        }
    }

    @Override
    public User obtemUsuarioPorId(Long id) {
        return pesquisaPorId(id);
    }

    private User pesquisaPorId(Long id){
        return userRepository.findById(id).stream().findFirst().orElseThrow(()-> new IllegalRequestException(MSG_USER_NOT_FOUND));
    }

    @Override
    public List<User> pesquisarPorTipoUser(TypeUser typeUser) {
        return userRepository.findByTypeUser(typeUser);
    }

    @Override
    public void recoverPassword(RecoverPassword recoverPassword) {
        User user = obtemUsuarioPorEmail(recoverPassword.email());

        if(UtilObjeto.isEmpty(user))
            throw new IllegalRequestException(MSG_USER_NOT_FOUND);

        String novaSenha =  UtilControl.gerarSenha(8);
        user.setNmSenha(config.passwordEncoder().encode(novaSenha));
        user.setPrimeiroAcesso(Boolean.TRUE);
        userRepository.save(user);

        emailNegocio.enviarEmailRecupearSenha(user, novaSenha);
    }

    private User obtemUsuarioPorEmail(String email){
        return userRepository.obtemUsuarioPorEmail(email);
    }
}

