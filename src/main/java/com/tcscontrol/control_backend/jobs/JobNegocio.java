package com.tcscontrol.control_backend.jobs;

public interface JobNegocio {
    
    static final String ASSUNTO_EMAIL_GESTOR_VENCER = "Patrimônios Com  Prazo de Entrega Próximo A vencer!"; 
    static final String MSG_GESTOR_REQUISICOES_VENCER = "Você possui patrimônios que devem ser entregues. Caso for de seu interesse entre contato com administrador para renovar o seu pedido. Segue os patrimônios;";
    
    static final String ASSUNTO_EMAIL_ADMIN = "Informações dos Patrimônio DATA!"; 
    static final String MSG_ADMIN_REQUISICOES = "Segue informações dos patrimônios do dia:";

    static final String ASSUNTO_EMAIL_GESTOR_VENCIDAS = "Requisições Atrasadas Para Entrega!";
    static final String MSG_GESTOR_REQUISICOES_VENCIDOS = "Você possui patrimônios que deveriam ter sido entregue, entre em contato com administrador e regularize a situação. Segue os patrimônio;";
    static final String MSG_ADMIN_REQUISICOES_VENCIDOS = "Segue a lista de patrimônio não devolvidos:";
}
