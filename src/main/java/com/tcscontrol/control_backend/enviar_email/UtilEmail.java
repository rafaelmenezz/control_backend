package com.tcscontrol.control_backend.enviar_email;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.model.entity.Allocation;
import com.tcscontrol.control_backend.allocation_patrimony.AllocationPatrimonyRepository;
import com.tcscontrol.control_backend.allocation_patrimony.model.entity.AllocationPatrimony;
import com.tcscontrol.control_backend.contacts.model.Contacts;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.enuns.TypeContacts;
import com.tcscontrol.control_backend.enuns.TypeUser;
import com.tcscontrol.control_backend.enviar_email.templates.TagsHtml;
import com.tcscontrol.control_backend.enviar_email.templates.TemplateEmail;
import com.tcscontrol.control_backend.exception.IllegalRequestException;
import com.tcscontrol.control_backend.maintenance.model.entity.Maintenance;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.pessoa.user.model.UserRepository;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;
import com.tcscontrol.control_backend.utilitarios.UtilCast;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;
import com.tcscontrol.control_backend.utilitarios.UtilString;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Component(value = "emailNegocio")
@AllArgsConstructor
public class UtilEmail implements EmailNegocio, TemplateEmail, TagsHtml {

    private final JavaMailSender javaMailSender;
    private UserRepository userRepository;
    private AllocationPatrimonyRepository allocationPatrimonyRepository;

    private void sendRegistrationEmail(String to, String subject, String text) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            helper.addInline("banner", new ClassPathResource("static/imagens/banner_email.jpg"));

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new IllegalArgumentException(EXCEPTION_MSG_ERRO_EMAIL + e.getMessage());
        }

    }

    public void enviarEmailNovoUsuario(User usuario, String senha) {
        String emailText = TEMPLATE_EMAIL;
        String saudacao = montarTitulo(usuario);
        String mensagem = montaMensagemNovoUsuario(senha);
        String email = obtemEmailUsuario(usuario);

        emailText = emailText.replace(TEMPLATE_SAUDACAO, saudacao);
        emailText = emailText.replace(TEMPLATE_MENSAGEM, "<p>" + mensagem + "</p>");
        sendRegistrationEmail(email, MSG_BOAS_VINDAS, emailText);
    }

    @Override
    public void enviarEmailNovaAlocacao(User usuario, Department department, List<Patrimony> patrimonies) {
        String emailText = TEMPLATE_EMAIL;
        String saudacao = montarTitulo(usuario);
        String mensagem = "";
        //String mensagem = montaMensagemNovaAlocacao(department.getNmDepartamento(), listarPatrimonios(patrimonies));
        String email = obtemEmailUsuario(usuario);

        emailText = emailText.replace(TEMPLATE_SAUDACAO, saudacao);
        emailText = emailText.replace(TEMPLATE_MENSAGEM, mensagem);
        sendRegistrationEmail(email, MSG_ASSUNTO_ALOCACAO, emailText);
    }

    @Override
    public void enviarEmailAlocacao(Allocation allocation, String mensagem) {
        User usuario = allocation.getDepartamento().getUser();
        String emailText = TEMPLATE_EMAIL;
        String saudacao = montarTitulo(usuario);
        String corpo = montaMensagemNovaAlocacao(allocation, mensagem);
        String email = obtemEmailUsuario(usuario);

        emailText = emailText.replace(TEMPLATE_SAUDACAO, saudacao);
        emailText = emailText.replace(TEMPLATE_MENSAGEM, corpo);
        sendRegistrationEmail(email, MSG_ASSUNTO_ALOCACAO, emailText);
    }

    @Override
    public void enviarEmailDevolucaoAlocacao(User usuario, List<Patrimony> patrimonies) {
        String emailText = TEMPLATE_EMAIL;
        String saudacao = montarTitulo(usuario);
        String mensagem = montaMensagemDevolucaoAlocacao(listarPatrimonios(patrimonies));
        String email = obtemEmailUsuario(usuario);

        emailText = emailText.replace(TEMPLATE_SAUDACAO, saudacao);
        emailText = emailText.replace(TEMPLATE_MENSAGEM, mensagem);
        sendRegistrationEmail(email, MSG_ASSUNTO_ALOCACAO, emailText);
    }

    @Override
    public void enviarEmailAgendaManutencao(Maintenance maintenance) {
        List<User> administradores = userRepository.findByTypeUser(TypeUser.ADMIN);
        AllocationPatrimony allocationPatrimony = allocationPatrimonyRepository.pesquisAllocationPatrimonyPorIdPatrimonio(maintenance.getPatrimony().getId());
        if (UtilObjeto.isNotEmpty(allocationPatrimony)) {
            User gestor = allocationPatrimony.getAllocation().getDepartamento().getUser();
            administradores.add(gestor);
        }
        String mensagem = montarMensagemAgendamentoManutencao(maintenance, MSG_AGENDAR_MANUTENCAO);

        for (User user : administradores) {
            enviarEmailAdministrador(user, TEMPLATE_EMAIL, mensagem);
        }

    }

    @Override
    public void enviarEmailIniciarManutencao(Maintenance maintenance) {
        List<User> administradores = userRepository.findByTypeUser(TypeUser.ADMIN);
        String mensagem = montarMensagemAgendamentoManutencao(maintenance, MSG_INICIAR_MANUTENCAO);
        AllocationPatrimony allocationPatrimony = allocationPatrimonyRepository.pesquisAllocationPatrimonyPorIdPatrimonio(maintenance.getPatrimony().getId());
        if (UtilObjeto.isNotEmpty(allocationPatrimony)) {
            User gestor = allocationPatrimony.getAllocation().getDepartamento().getUser();
            administradores.add(gestor);
        }
        for (User user : administradores) {
            enviarEmailAdministrador(user, TEMPLATE_EMAIL, mensagem);
        }

    }

    @Override
    public void enviarEmailFinalizarManutencao(Maintenance maintenance) {
        List<User> administradores = userRepository.findByTypeUser(TypeUser.ADMIN);
        String mensagem = montarMensagemAgendamentoManutencao(maintenance, MSG_FINALIZADA_MANUTENCAO);
        AllocationPatrimony allocationPatrimony = allocationPatrimonyRepository.pesquisAllocationPatrimonyPorIdPatrimonio(maintenance.getPatrimony().getId());
        if (UtilObjeto.isNotEmpty(allocationPatrimony)) {
            User gestor = allocationPatrimony.getAllocation().getDepartamento().getUser();
            administradores.add(gestor);
        }
        for (User user : administradores) {
            enviarEmailAdministrador(user, TEMPLATE_EMAIL, mensagem);
        }

    }

    @Override
    public void enviarEmailCancelarManutencao(Maintenance maintenance) {
        List<User> administradores = userRepository.findByTypeUser(TypeUser.ADMIN);
        String mensagem = montarMensagemAgendamentoManutencao(maintenance, MSG_CANCELADA_MANUTENCAO);
        AllocationPatrimony allocationPatrimony = allocationPatrimonyRepository.pesquisAllocationPatrimonyPorIdPatrimonio(maintenance.getPatrimony().getId());
        if (UtilObjeto.isNotEmpty(allocationPatrimony)) {
            User gestor = allocationPatrimony.getAllocation().getDepartamento().getUser();
            administradores.add(gestor);
        }
        for (User user : administradores) {
            enviarEmailAdministrador(user, TEMPLATE_EMAIL, mensagem);
        }

    }

    private String montarMensagemAgendamentoManutencao(Maintenance maintenance, String tpMensagem) {
        String dtPrevisao = UtilData.toString(maintenance.getDtAgendamento(), UtilData.FORMATO_DDMMAA);
        String dtInicio = UtilObjeto.isNotEmpty(maintenance.getDtEntrada())
                ? UtilData.toString(maintenance.getDtEntrada(), UtilData.FORMATO_DDMMAA)
                : UtilString.EMPTY;
        String dtFim = UtilObjeto.isNotEmpty(maintenance.getDtFim())
                ? UtilData.toString(maintenance.getDtFim(), UtilData.FORMATO_DDMMAA)
                : UtilString.EMPTY;
        String mensagem = tpMensagem;
        String nmPatriminio = maintenance.getPatrimony().getNmPatrimonio();
        String tpManutencao = maintenance.getTpManutencao().getValue();
        String motivo = UtilObjeto.isNotEmpty(maintenance.getDsMotivoManutencao()) ? maintenance.getDsMotivoManutencao() : UtilString.EMPTY ;
        String descricao = UtilObjeto.isNotEmpty(maintenance.getDsObservacao()) ? maintenance.getDsObservacao() : UtilString.EMPTY ;
        String valor = UtilObjeto.isNotEmpty(maintenance.getVlManutencao()) ? UtilCast.toString(maintenance.getVlManutencao()) : UtilString.EMPTY;
        String nmFornecedor = maintenance.getFornecedor().getNmName();

        mensagem = mensagem.replaceAll(NM_PATRIMONIO, nmPatriminio);
        StringBuilder detalhes = new StringBuilder();

        if (UtilString.isNotEmpty(nmPatriminio))
            detalhes.append(itemLista.replace(TEXTO, "<b>Patrimônio: </b>" + nmPatriminio));

        if (UtilString.isNotEmpty(dtPrevisao))
            detalhes.append(itemLista.replace(TEXTO, "<b>Data de Previsão Manutenção: </b>" + dtPrevisao));

        if (UtilString.isNotEmpty(dtInicio))
            detalhes.append(itemLista.replace(TEXTO, "<b>Data Início da Manutenção: </b>" + dtInicio));

        if (UtilString.isNotEmpty(dtFim))
            detalhes.append(itemLista.replace(TEXTO, "<b>Data Fim da Manutenção: </b>" + dtFim));

         if (UtilString.isNotEmpty(nmFornecedor))
            detalhes.append(itemLista.replace(TEXTO, "<b>Forncedor: </b>" + nmFornecedor));

        if (UtilString.isNotEmpty(tpManutencao))
            detalhes.append(itemLista.replace(TEXTO, "<b>Tipo de Manutenção: </b>" + tpManutencao));
        
        if (UtilString.isNotEmpty(valor) && !valor.equalsIgnoreCase("0.0"))
            detalhes.append(itemLista.replace(TEXTO, "<b>Valor da Manutenção:</b> " + valor));

        if (UtilString.isNotEmpty(motivo))
            detalhes.append(itemLista.replace(TEXTO, "<b>Motivo:</b> " + motivo));

        if (UtilString.isNotEmpty(descricao))
            detalhes.append(itemLista.replace(TEXTO, "<b>Observações:</b> " + descricao));
        

        String detalhesPatrimonio = listNaoOrdenada.replace(CONTEUDO, detalhes.toString());
        mensagem = mensagem.concat(detalhesPatrimonio);

        return mensagem;
    }

    private void enviarEmailAdministrador(User administrador, String template, String mensagem) {
        String emailText = template;
        String saudacao = montarTitulo(administrador);
        String email = obtemEmailUsuario(administrador);

        emailText = emailText.replace(TEMPLATE_SAUDACAO, saudacao);
        emailText = emailText.replace(TEMPLATE_MENSAGEM, mensagem);

        sendRegistrationEmail(email, ASSUNTO_EMAIL_MANUTENCAO, emailText);
    }

    private String montarTitulo(User usuario) {
        String titulo = TITULO_NOVO_USUARIO;
        titulo = titulo.replace("NM_USUARIO", usuario.getNmName());
        return titulo;
    }

    private String montaMensagemNovoUsuario(String senha) {
        String mensagem = MSG_NOVO_USUARIO;
        mensagem = mensagem.replace("SENHA_ACESSO", "<b>" + senha + "</b>");

        return mensagem;
    }

    private String montaMensagemNovaAlocacao(Allocation allocation, String mensagem) {
        Department departamento = allocation.getDepartamento();
        
        StringBuilder retorno = new StringBuilder();
        retorno.append("<p>").append(mensagem.replace(NM_DEPARTAMENTO, departamento.getNmDepartamento())).append("</p>");
        retorno.append(listarPatrimonios(obtemListaPatrimonios(allocation)));

        return retorno.toString();

    }

    private String montaMensagemDevolucaoAlocacao(String patrimonios) {
        StringBuilder retorno = new StringBuilder();

        retorno.append("<p>").append(MSG_DEVOLVER_ALOCACAO).append("</p>");
        retorno.append(patrimonios);

        return retorno.toString();

    }

    private String listarPatrimonios(List<Patrimony> patrimonies) {
        StringBuilder retorno = new StringBuilder();

        retorno.append("<ul>");

        for (Patrimony patrimony : patrimonies) {
            retorno.append("<li>").append(patrimony.getNmPatrimonio()).append(";</li>");
        }
        retorno.append("</ul>");

        return retorno.toString();
    }

    private String obtemEmailUsuario(User usuario) {
        Contacts contato = usuario.getContacts().stream().filter(c -> TypeContacts.EMAIL.equals(c.getTypeContacts()))
                .findFirst().orElseThrow(() -> new IllegalRequestException("E-mail inválido!"));
        return contato.getDsContato();
    }

    private List<Patrimony> obtemListaPatrimonios(Allocation allocation){
        List<AllocationPatrimony> aps = allocation.getPatrimonios();
        List<Patrimony> patrimonies = new ArrayList<>();

        for (AllocationPatrimony allocationPatrimony : aps) {
            patrimonies.add(allocationPatrimony.getPatrimony());
        }
        return patrimonies;

    }

}
