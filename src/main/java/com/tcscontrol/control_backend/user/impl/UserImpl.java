package com.tcscontrol.control_backend.user.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;


import com.tcscontrol.control_backend.auth.model.RefreshToken;
import com.tcscontrol.control_backend.auth.repository.RefreshTokenRepository;
import com.tcscontrol.control_backend.contacts.ContactsRepository;
import com.tcscontrol.control_backend.contacts.model.Contacts;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.user.UserNegocio;
import com.tcscontrol.control_backend.user.impl.mapper.UserMapper;
import com.tcscontrol.control_backend.user.model.UserRepository;
import com.tcscontrol.control_backend.user.model.dto.UserDTO;
import com.tcscontrol.control_backend.user.model.dto.UserSenhaDTO;
import com.tcscontrol.control_backend.user.model.entity.User;
import com.tcscontrol.control_backend.utilitarios.EmailService;
import com.tcscontrol.control_backend.utilitarios.UtilControl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@Component(value = "userNegocio")
@AllArgsConstructor
public class UserImpl implements UserNegocio {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ContactsRepository contactsRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;

    public List<UserDTO> list() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(@NotNull @Positive Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Override
    public UserDTO create(@Valid @NotNull UserDTO userDto) {
        UserDTO userD = userDto;      
        String email = userD.contacts().get(0).dsContato();
        String emailText = "Sua senha de cadastro: " + UtilControl.gerarSenha(8);
        emailService.sendRegistrationEmail(email, "Bem-vindo!", emailText);
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

    @Override
    public UserDTO update(Long id, @Valid @NotNull UserDTO userDto) {
        return userRepository.findById(id)
                .map(recordFound -> {
                    User user = userMapper.toEntity(userDto);
                    recordFound.setNmUsuario(userDto.nmUsuario());
                    recordFound.setFtFoto(userDto.ftFoto());
                    recordFound.setTypeUser(userMapper.convertTypeUserValue(userDto.typeUser()));
                    recordFound.setFlStatus(userMapper.convertStatusValue(userDto.flStatus()));
                    recordFound.getContacts().clear();
                    user.getContacts().forEach(recordFound.getContacts()::add);
                    return userMapper.toDto(userRepository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Override
    public void delete(@NotNull @Positive Long id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
    }

    @Override
    public Optional<User> login(String login) {

        // Optional<User> retornoMatricula =
        // pesquisarUserMatricula(Integer.parseInt(login));
        // if(!retornoMatricula.isEmpty()){
        // return retornoMatricula;
        // }
        if (login.contains("-")) {
            Optional<User> retornoCPF = pesquisarUserDocumento(login);
            return retornoCPF;
        }
        if (login.contains("@")) {
            Optional<User> retornoEmail = pesquisarUserPorContato(login);
            return retornoEmail;
        }

        return pesquisarUserMatricula(login);

    }

    @Override
    public void deleteCascade(Integer matricula) {
        Optional<User> user = userRepository.findByNrMatricula(matricula);
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
        userMapper.toDto(userRepository.save(userMapper.toRegisterEntity(user)));
    }

    private Optional<User> pesquisarUserMatricula(String matricula) {
        return userRepository.findByNrMatricula(Integer.parseInt(matricula));

    }

    private Optional<User> pesquisarUserDocumento(String documento) {
        return userRepository.findByNrCpf(documento);
    }

    private Optional<User> pesquisarUserPorContato(String dsContato) {
        Contacts contato = contactsRepository.findByDsContato(dsContato);
        if (contato != null) {
            Optional<User> retorno = Optional.ofNullable(contato.getUser());
            return retorno;
        }
        return null;
    }

}
