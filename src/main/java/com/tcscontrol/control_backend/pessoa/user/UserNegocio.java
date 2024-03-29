package com.tcscontrol.control_backend.pessoa.user;

import java.util.List;

import com.tcscontrol.control_backend.enuns.TypeUser;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

public interface UserNegocio extends UserService {

    public static final String MSG_USER_NOT_FOUND = "Usuário não encontrado!";

    User obtemUsuarioPorId(Long id);

    List<User> pesquisarPorTipoUser(TypeUser typeUser);

}
