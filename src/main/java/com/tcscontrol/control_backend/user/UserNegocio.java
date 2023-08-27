package com.tcscontrol.control_backend.user;

import com.tcscontrol.control_backend.user.model.dto.UserSenhaDTO;

public interface UserNegocio extends UserService {
    
    void deleteCascade(Integer matricula);

    void register(UserSenhaDTO user);
}
