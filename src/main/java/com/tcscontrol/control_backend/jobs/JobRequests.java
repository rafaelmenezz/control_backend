package com.tcscontrol.control_backend.jobs;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.tcscontrol.control_backend.enviar_email.EmailNegocio;
import com.tcscontrol.control_backend.request_patrimony.model.entity.RequestPatrimony;
import com.tcscontrol.control_backend.requests.RequestNegocio;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@Transactional
public class JobRequests implements JobNegocio {

    private RequestNegocio requestNegocio;
    private EmailNegocio emailNegocio;

    @Scheduled(fixedRate = 300000)
    //@Scheduled(cron = "0 10 0 ? * *")
    public void verificarDataHoraAtual() {
        enviarEmailPatrimonioDevolucaoAVencer();
        enviarEmailPatrimonioDevolucaoVencidos();
        enviarEmailAdminPatrimonios();
        System.out.println("Job envio de email - Finalizado!");
    }

    private void enviarEmailPatrimonioDevolucaoAVencer() {
        List<Map<String,Object>> r = requestNegocio.obterPatrimoniosAVencer();

        if (UtilObjeto.isEmpty(r) || r.size() == 0)
            return;

        for (Map<String,Object> requests : r) {
            emailNegocio.enviarEmailRequisicoesGestor(requests, MSG_GESTOR_REQUISICOES_VENCER, ASSUNTO_EMAIL_GESTOR_VENCER);
            System.out.println("Envio de e-mail dos patrimônios a serem devolvidos!");
        }
    }

    private void enviarEmailPatrimonioDevolucaoVencidos() {
        List<Map<String,Object>> r = requestNegocio.obterPatrimoniosVencidos();

        if (UtilObjeto.isEmpty(r) || r.size() == 0)
            return;

        for (Map<String,Object> requests : r) {
            emailNegocio.enviarEmailRequisicoesGestor(requests, MSG_GESTOR_REQUISICOES_VENCIDOS, ASSUNTO_EMAIL_GESTOR_VENCIDAS);
            System.out.println("Envio de e-mail patrimônios não devolvidos!");
        }
    }

    private void enviarEmailAdminPatrimonios(){
        Map<String,Object> dados = requestNegocio.obterPatrimonioEnvioEmailAdmin();

        if (UtilObjeto.isEmpty(dados))
            return; 

        emailNegocio.enviarEmailAdminJobPatrimonio(dados, MSG_ADMIN_REQUISICOES, ASSUNTO_EMAIL_ADMIN.replace("DATA", UtilData.toString(new Date(), UtilData.FORMATO_DDMMAA)));
        System.out.println("Envio de e-mail Administradores!");
    }

}
