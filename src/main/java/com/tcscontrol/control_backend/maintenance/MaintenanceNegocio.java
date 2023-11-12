package com.tcscontrol.control_backend.maintenance;

import org.springframework.stereotype.Component;

@Component
public interface MaintenanceNegocio extends MaintenanceService {

    String EXCEPTION_MSG_ERRO_DATA_AGENDAMENTO_INVALIDA = "Não foi possivel agendar manutenção, data de agendamento não informada ou inválida!";
    String EXCEPTION_MSG_ERRO_DATA_INICIAL_INVALIDA = "Não foi possivel de iniciar de manutenção, data de inicio não informada ou inválida!";
    String EXCEPTION_MSG_ERRO_DATA_FINAL_INVALIDA = "Não foi possivel finalizar manutenção, data final não informada ou invalida!";
    String EXCEPTION_MSG_ERRO_OBSERVATION_NOT_NULL = "Não foi possível cancelar manutenção, necessário informar o motivo do cancelamento!";
    String MSG_CANCELAMENTO_MANUTENCAO = "\n \nManutentação Cancelada!";
      
}
