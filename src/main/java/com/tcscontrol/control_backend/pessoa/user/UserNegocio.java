package com.tcscontrol.control_backend.pessoa.user;

import com.tcscontrol.control_backend.pessoa.user.model.dto.UserSenhaDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserNegocio extends UserService {
    
    void deleteCascade(Integer nrMatricula);

    void register(UserSenhaDTO user);

    UserDetails userLogin(String login);
}
