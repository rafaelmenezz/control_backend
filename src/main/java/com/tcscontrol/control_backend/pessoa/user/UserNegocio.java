package com.tcscontrol.control_backend.pessoa.user;

import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

public interface UserNegocio extends UserService {

    User obtemUsuarioPorId(Long id);
    
}
