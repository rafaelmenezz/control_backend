package com.tcscontrol.control_backend.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobRequests {
    

    @Scheduled(fixedRate = 20000)
    // @Scheduled(cron =  "0 0 0 * * ?")
    public void verificarDataHoraAtual(){
        System.out.println("Executando o Agendador de Tarefas");
    }
}
