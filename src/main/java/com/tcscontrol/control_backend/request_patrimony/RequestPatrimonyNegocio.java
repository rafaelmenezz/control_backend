package com.tcscontrol.control_backend.request_patrimony;

import org.springframework.stereotype.Component;

@Component
public interface RequestPatrimonyNegocio extends RequestPatrimonyService {

    public final Integer AGENDAR = 1;
    public final Integer RETIRAR = 2;
    public final Integer FINALIZAR = 3; 

    public final String MSG_ERRO_DATA_PREVISAO_NAO_INFORMADA = "Data de agendamento não informada!";
    public final String MSG_ERRO_DATA_INICIO_NAO_INFORMADA = "Data de retirada não informada!";
    public final String MSG_ERRO_DATA_FIM_NAO_INFORMADA = "Data de devolução não informada!";
    
}
