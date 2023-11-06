package com.tcscontrol.control_backend.enviar_email;

import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.contacts.model.Contacts;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.enuns.TypeContacts;
import com.tcscontrol.control_backend.exception.IllegalRequestException;
import com.tcscontrol.control_backend.maintenance.model.entity.Maintenance;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Component(value = "emailNegocio")
@AllArgsConstructor
public class UtilEmail implements EmailNegocio, TemplateEmail{

    private final JavaMailSender javaMailSender;

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
            
        }

    }

    public void enviarEmail(String email, String senha){
        String emailText = "Sua senha de cadastro: " + senha;
        sendRegistrationEmail(email, "Bem-vindo!", emailText);
    }

    public void enviarEmailNovoUsuario(User usuario, String senha){
        String emailText = TEMPLATE_EMAIL;
        String saudacao = montarTituloNovoUsuario(usuario);
        String mensagem = montaMensagemNovoUsuario(senha);
        String email = obtemEmailUsuario(usuario);

        emailText = emailText.replace(TEMPLATE_SAUDACAO, saudacao);
        emailText = emailText.replace(TEMPLATE_MENSAGEM, "<p>" + mensagem + "</p>");
        sendRegistrationEmail(email, "Bem-vindo!", emailText);
    }

    @Override
    public void enviarEmailNovaAlocacao(User usuario, Department department, List<Patrimony> patrimonies) {
        String emailText = TEMPLATE_EMAIL;
        String saudacao = montarTituloNovoUsuario(usuario);
        String mensagem = montaMensagemNovaAlocacao(department.getNmDepartamento(), listarPatrimonios(patrimonies));
        String email = obtemEmailUsuario(usuario);

        emailText = emailText.replace(TEMPLATE_SAUDACAO, saudacao);
        emailText = emailText.replace(TEMPLATE_MENSAGEM, mensagem);
        sendRegistrationEmail(email, MSG_ASSUNTO_ALOCACAO, emailText);
    }
    
    @Override
    public void enviarEmailDevolucaoAlocacao(User usuario, List<Patrimony> patrimonies) {
        String emailText = TEMPLATE_EMAIL;
        String saudacao = montarTituloNovoUsuario(usuario);
        String mensagem = montaMensagemDevolucaoAlocacao(listarPatrimonios(patrimonies));
        String email = obtemEmailUsuario(usuario);

        emailText = emailText.replace(TEMPLATE_SAUDACAO, saudacao);
        emailText = emailText.replace(TEMPLATE_MENSAGEM, mensagem);
        sendRegistrationEmail(email, MSG_ASSUNTO_ALOCACAO, emailText);
    }

    @Override
    public void enviarEmailAgendaManutencao(Maintenance maintenance, User user) {
        String emailText = TEMPLATE_EMAIL;
        throw new UnsupportedOperationException("Unimplemented method 'enviarEmailAgendaManutencao'");
    }

    private String montarTituloNovoUsuario(User usuario){
        String titulo = TITULO_NOVO_USUARIO;
        titulo = titulo.replace("NM_USUARIO", usuario.getNmName());
        return titulo;
    }

    private String montaMensagemNovoUsuario(String senha){
        String mensagem = MSG_NOVO_USUARIO;
        mensagem = mensagem.replace("SENHA_ACESSO","<b>"+ senha + "</b>");

        return mensagem;
    }

    private String montaMensagemNovaAlocacao(String departamento, String patrimonios){
        StringBuilder retorno = new StringBuilder();

        retorno.append("<p>").append(MSG_NOVA_ALOCACAO.replace("NM_DEPARTAMENTO",departamento)).append("</p>");
        retorno.append(patrimonios);
        

        return retorno.toString();

    }

    private String montaMensagemDevolucaoAlocacao( String patrimonios){
        StringBuilder retorno = new StringBuilder();

        retorno.append("<p>").append(MSG_DEVOLVER_ALOCACAO).append("</p>");
        retorno.append(patrimonios);
        

        return retorno.toString();

    }
    
    private String listarPatrimonios(List<Patrimony> patrimonies){
        StringBuilder retorno = new StringBuilder();

        retorno.append("<ul>");

        for (Patrimony patrimony : patrimonies) {
            retorno.append("<li>").append(patrimony.getNmPatrimonio()).append(";</li>");
        }
        retorno.append("</ul>");

        return retorno.toString();
    }

    private String obtemEmailUsuario(User usuario){
        Contacts contato = usuario.getContacts().stream().filter(c -> TypeContacts.EMAIL.equals(c.getTypeContacts())).findFirst().orElseThrow(()->  new IllegalRequestException("E-mail inválido!"));
        return contato.getDsContato();
    }







}
