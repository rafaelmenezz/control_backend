package com.tcscontrol.control_backend.enviar_email;

import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

public interface EmailNegocio {

    String TITULO_NOVO_USUARIO = "Prezado(a) NM_USUARIO";
    String MSG_NOVO_USUARIO = "Sua senha de acesso é SENHA_ACESSO. Será necessário efetuar a substituição da senha no primeiro acesso.";
    
    void enviarEmail(String email, String senha);
    void enviarEmailNovoUsuario(User usuario, String senha);


}
