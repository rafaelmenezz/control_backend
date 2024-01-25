package com.tcscontrol.control_backend.inventory;

import org.springframework.stereotype.Component;

@Component
public interface InventoryNegocio extends InventoryService {
    
    static final String DT_AGENDAMENTO = "dtAgendamento";
    static final String ASSUNTO = "assunto";
    static final String MSG_EMAIL_ADMIN = "msgEmailAdmin";
    static final String MSG_EMAIL_GESTOR = "msgEmailGestor";
    static final String DATA_INVENTARIO = "DATA_INVENTARIO";

    static final String MSG_ASSUNTO_INVENTARIO_INVENTARIO = "Data de Inventário Agendada!";
    static final String MSG_EMAIL_ADMINISTRADOR_INVENTARIO_AGENDADO = "Data para realização do inventário foi cadastrada para o dia DATA_INVENTARIO.";
    static final String MSG_EMAIL_GESTORES_INVENTARIO_AGENDADO = "Informamos que foi agendada uma data para a realização do inventário dos nossos patrimônios, que ocorrerá no dia 29/11/2023. Para obter mais informações, entre em contato com o administrador.";
}
