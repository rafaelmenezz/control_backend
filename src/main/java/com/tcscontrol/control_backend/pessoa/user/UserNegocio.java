package com.tcscontrol.control_backend.pessoa.user;

import com.tcscontrol.control_backend.pessoa.user.model.dto.UserSenhaDTO;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public interface UserNegocio extends UserService {
    
    void deleteCascade(Integer nrMatricula);

    void register(UserSenhaDTO user);

    UserDetails userLogin(String login);

    User obtemUserMatricula(String matricula);
}
