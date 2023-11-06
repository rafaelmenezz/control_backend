package com.tcscontrol.control_backend.enviar_email;

import java.util.List;

import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.maintenance.model.entity.Maintenance;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

public interface EmailNegocio {

    String TITULO_NOVO_USUARIO = "Prezado(a) NM_USUARIO,";
    String MSG_NOVO_USUARIO = "Sua senha de acesso é SENHA_ACESSO. Será necessário efetuar a substituição da senha no primeiro acesso.";

    String MSG_NOVA_ALOCACAO = "Sua alocação para <b> NM_DEPARTAMENTO </b> foi registrada com sucesso, segue os patrimônios alocados:";
    String MSG_DEVOLVER_ALOCACAO = "Os patrimônios foram devolvidos com sucesso, segue os patrimonios devolvidos:";
    String MSG_ASSUNTO_ALOCACAO = "Alocação registrada com sucesso | NÃO-RESPONDER!";

    String MSG_AGENDAR_MANUTENCAO = "Manutenção do patrimonônio NM_PATRIMONIO foi agendado com sucesso, segue os dados:";
    
    void enviarEmail(String email, String senha);
    void enviarEmailNovoUsuario(User usuario, String senha);
    void enviarEmailNovaAlocacao(User usuario, Department department, List<Patrimony> patrimonies);
    void enviarEmailDevolucaoAlocacao(User usuario, List<Patrimony> patrimonies);
    void enviarEmailAgendaManutencao(Maintenance maintenance, User user);



}
