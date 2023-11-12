package com.tcscontrol.control_backend.request_patrimony;

import org.springframework.stereotype.Component;

@Component
public interface RequestPatrimonyNegocio extends RequestPatrimonyService {

    public final Integer AGENDAR = 1;
    public final Integer RETIRAR = 2;
    public final Integer FINALIZAR = 3; 

    public final String MSG_EMAIL_SOLICITAR_REQUISICAO = "Sua solicitação de patrimônios para obra <b>NM_OBRA</b> foi <b>registrada</b> com sucesso. Aguarde a confirmação do Administrador para retirada. Segue os dados da sua solicitação:";
    public final String MSG_EMAIL_RETIRADA_REQUISICAO = "Sua solicitação de patrimônios para obra <b>NM_OBRA</b>, foi <b>aprovada</b>. Segue os dados:";
    public final String MSG_EMAIL_REJEITADA_REQUISICAO = "Sua solicitação de patrimônios para obra <b>NM_OBRA</b>, foi <b>rejeitada</b>. Segue os dados:";
    public final String MSG_EMAIL_DEVOLUCAO_REQUISICAO = "Os patrimônios alocados na obra <b>NM_OBRA</b> foram <b>devolvidos</b>. Segue os dados:";
    public final String MSG_NOVA_REQUISICAO_ADMIN = "Uma requisição de equipamentos foi aberto para obra <b>NM_OBRA</b>. Segue os itens requisitados:";
    public final String MSG_RETIRADA_REQUISICAO_ADMIN = "Uma requisição de retirada de equipamentos foi <b>aprovada</b> para obra <b>NM_OBRA</b>. Segue os dados dos itens alocados:";
    public final String MSG_REJEITA_REQUISICAO_ADMIN = "Uma requisição de retirada de equipamentos foi <b>rejeitada</b> para obra <b>NM_OBRA</b>. Segue os dados dos itens rejeitados:";
    public final String MSG_DEVOLUCAO_REQUISICAO_ADMIN = "Os equipamentos alocados na obra <b>NM_OBRA</b> foram <b>devolvidos</b>. Segue os dados dos itens devolvidos:";
    public final String MSG_ERRO_DATA_PREVISAO_NAO_INFORMADA = "Data de agendamento não informada!";
    public final String MSG_ERRO_DATA_INICIO_NAO_INFORMADA = "Data de retirada não informada!";
    public final String MSG_ERRO_DATA_FIM_NAO_INFORMADA = "Data de devolução não informada!";
    
}
