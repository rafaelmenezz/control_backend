package com.tcscontrol.control_backend.enviar_email;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.contacts.model.Contacts;
import com.tcscontrol.control_backend.enuns.TypeContacts;
import com.tcscontrol.control_backend.exception.IllegalRequestException;
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
        Contacts contato = usuario.getContacts().stream().filter(c -> TypeContacts.EMAIL.equals(c.getTypeContacts())).findFirst().orElseThrow(()->  new IllegalRequestException("E-mail inválido!"));
        String email = contato.getDsContato();

        emailText = emailText.replace("{SAUDACAO}", saudacao);
        emailText = emailText.replace("{CORPO_MENSAGEM}", mensagem);
        sendRegistrationEmail(email, "Bem-vindo!", emailText);




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
    

}
