package com.tcscontrol.control_backend.enviar_email;

import java.util.List;

import com.tcscontrol.control_backend.allocation.model.entity.Allocation;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.maintenance.model.entity.Maintenance;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

public interface EmailNegocio {

    public final String EXCEPTION_MSG_ERRO_EMAIL = "Não foi possivel enviar e-mail, ";
    public final String EXCEPTION_MSG_ERRO_NAO_ENCONTRADO_ADMINISTRADORES = "Não há administradores cadastrados";
    public final String EXCEPTION_MSG_ERRO_EMAIL_ADMINISTRADOR_NAO_CADASTRADO = "Administrador não possui e-mail cadastrado!";

    public final String NM_PATRIMONIO = "NM_PATRIMONIO";
    public final String NM_DEPARTAMENTO = "NM_DEPARTAMENTO";

    public final String MSG_BOAS_VINDAS = "Bem-vindo!";

    public final String TITULO_NOVO_USUARIO = "Prezado(a) NM_USUARIO,";
    public final String MSG_NOVO_USUARIO = "Sua senha de acesso é SENHA_ACESSO. Será necessário efetuar a substituição da senha no primeiro acesso.";

    public final String MSG_NOVA_ALOCACAO = "Sua alocação para <b> NM_DEPARTAMENTO </b> foi registrada com sucesso, segue os patrimônios alocados:";
    public final String MSG_DEVOLVER_ALOCACAO = "Os patrimônios foram devolvidos com sucesso, segue os patrimonios devolvidos:";
    public final  String MSG_ASSUNTO_ALOCACAO = "Alocação registrada com sucesso | NÃO-RESPONDER!";

    public final String MSG_AGENDAR_MANUTENCAO = "Manutenção do patrimonônio NM_PATRIMONIO foi agendado, segue os dados:";
    public final String MSG_INICIAR_MANUTENCAO = "Manutenção do patrimonônio NM_PATRIMONIO foi iniciada, segue os dados:";
    public final String MSG_FINALIZADA_MANUTENCAO = "Manutenção do patrimonônio NM_PATRIMONIO terminou, segue os dados:";
    public final String MSG_CANCELADA_MANUTENCAO = "Manutenção do patrimonônio NM_PATRIMONIO foi cancelada, segue os dados:";
    public final String ASSUNTO_EMAIL_MANUTENCAO = "Manutenção!";
    
    void enviarEmailNovoUsuario(User usuario, String senha);
    void enviarEmailNovaAlocacao(User usuario, Department department, List<Patrimony> patrimonies);
    void enviarEmailDevolucaoAlocacao(User usuario, List<Patrimony> patrimonies);
    void enviarEmailAgendaManutencao(Maintenance maintenance);
    void enviarEmailIniciarManutencao(Maintenance maintenance);
    void enviarEmailFinalizarManutencao(Maintenance maintenance);
    void enviarEmailCancelarManutencao(Maintenance maintenance);
    void enviarEmailAlocacao(Allocation allocation, String mensagem);



}
