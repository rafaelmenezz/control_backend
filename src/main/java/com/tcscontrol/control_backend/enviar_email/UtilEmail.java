package com.tcscontrol.control_backend.enviar_email;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.allocation.model.entity.Allocation;
import com.tcscontrol.control_backend.allocation_patrimony.AllocationPatrimonyRepository;
import com.tcscontrol.control_backend.allocation_patrimony.model.entity.AllocationPatrimony;
import com.tcscontrol.control_backend.constructions.model.entity.Construction;
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
import com.tcscontrol.control_backend.request_patrimony.model.entity.RequestPatrimony;
import com.tcscontrol.control_backend.requests.model.entity.Requests;
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

    @Override
    public void enviarEmailNovoUsuario(User usuario, String senha) {
        String emailText = TEMPLATE_EMAIL;
        String saudacao = montarTitulo(usuario);
        String mensagem = montaMensagemNovoUsuario(senha, MSG_NOVO_USUARIO);
        String email = obtemEmailUsuario(usuario);

        emailText = emailText.replace(TEMPLATE_SAUDACAO, saudacao);
        emailText = emailText.replace(TEMPLATE_MENSAGEM, "<p>" + mensagem + "</p>");
        sendRegistrationEmail(email, MSG_BOAS_VINDAS, emailText);
    }

    @Override
    public void enviarEmailRecupearSenha(User usuario, String senha) {
        String emailText = TEMPLATE_EMAIL;
        String saudacao = montarTitulo(usuario);
        String mensagem = montaMensagemNovoUsuario(senha, MSG_RECUPERACÃO_SENHA);
        String email = obtemEmailUsuario(usuario);

        emailText = emailText.replace(TEMPLATE_SAUDACAO, saudacao);
        emailText = emailText.replace(TEMPLATE_MENSAGEM, "<p>" + mensagem + "</p>");
        sendRegistrationEmail(email, MSG_ASSUNTO_RECUPERAR_SENHA, emailText);
    }

    @Override
    public void enviarEmailNovaAlocacao(User usuario, Department department, List<Patrimony> patrimonies) {
        String emailText = TEMPLATE_EMAIL;
        String saudacao = montarTitulo(usuario);
        String mensagem = "";
        // String mensagem = montaMensagemNovaAlocacao(department.getNmDepartamento(),
        // listarPatrimonios(patrimonies));
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
        AllocationPatrimony allocationPatrimony = allocationPatrimonyRepository
                .pesquisAllocationPatrimonyPorIdPatrimonio(maintenance.getPatrimony().getId());
        if (UtilObjeto.isNotEmpty(allocationPatrimony)) {
            User gestor = allocationPatrimony.getAllocation().getDepartamento().getUser();
            administradores.add(gestor);
        }
        String mensagem = montarMensagemAgendamentoManutencao(maintenance, MSG_AGENDAR_MANUTENCAO);

        for (User user : administradores) {
            enviarEmailAdministrador(user, TEMPLATE_EMAIL, ASSUNTO_EMAIL_MANUTENCAO, mensagem);
        }
    }

    @Override
    public void enviarEmailIniciarManutencao(Maintenance maintenance) {
        List<User> administradores = userRepository.findByTypeUser(TypeUser.ADMIN);
        String mensagem = montarMensagemAgendamentoManutencao(maintenance, MSG_INICIAR_MANUTENCAO);
        AllocationPatrimony allocationPatrimony = allocationPatrimonyRepository
                .pesquisAllocationPatrimonyPorIdPatrimonio(maintenance.getPatrimony().getId());
        if (UtilObjeto.isNotEmpty(allocationPatrimony)) {
            User gestor = allocationPatrimony.getAllocation().getDepartamento().getUser();
            administradores.add(gestor);
        }
        for (User user : administradores) {
            enviarEmailAdministrador(user, TEMPLATE_EMAIL, ASSUNTO_EMAIL_MANUTENCAO, mensagem);
        }
    }

    @Override
    public void enviarEmailFinalizarManutencao(Maintenance maintenance) {
        List<User> administradores = userRepository.findByTypeUser(TypeUser.ADMIN);
        String mensagem = montarMensagemAgendamentoManutencao(maintenance, MSG_FINALIZADA_MANUTENCAO);
        AllocationPatrimony allocationPatrimony = allocationPatrimonyRepository
                .pesquisAllocationPatrimonyPorIdPatrimonio(maintenance.getPatrimony().getId());
        if (UtilObjeto.isNotEmpty(allocationPatrimony)) {
            User gestor = allocationPatrimony.getAllocation().getDepartamento().getUser();
            administradores.add(gestor);
        }
        for (User user : administradores) {
            enviarEmailAdministrador(user, TEMPLATE_EMAIL, ASSUNTO_EMAIL_MANUTENCAO, mensagem);
        }
    }

    @Override
    public void enviarEmailCancelarManutencao(Maintenance maintenance) {
        List<User> administradores = userRepository.findByTypeUser(TypeUser.ADMIN);
        String mensagem = montarMensagemAgendamentoManutencao(maintenance, MSG_CANCELADA_MANUTENCAO);
        AllocationPatrimony allocationPatrimony = allocationPatrimonyRepository
                .pesquisAllocationPatrimonyPorIdPatrimonio(maintenance.getPatrimony().getId());
        if (UtilObjeto.isNotEmpty(allocationPatrimony)) {
            User gestor = allocationPatrimony.getAllocation().getDepartamento().getUser();
            administradores.add(gestor);
        }
        for (User user : administradores) {
            enviarEmailAdministrador(user, TEMPLATE_EMAIL, ASSUNTO_EMAIL_MANUTENCAO, mensagem);
        }
    }

    @Override
    public void enviarEmailRequisicoes(Requests requests, String mensagem, String mensagemAdm) {
        enviarEmailRequisicoes(requests, mensagem, mensagemAdm, null, null);
    }

    @Override
    public void enviarEmailRequisicoes(Requests requests, String mensagem, String mensagemAdm, String assunto, String assuntoAdm) {
        User usuario = requests.getConstruction().getUser();
        String emailText = TEMPLATE_EMAIL;
        String saudacao = montarTitulo(usuario);
        String corpo = montaCorpoEmailRequisicao(requests, mensagem);
        String email = obtemEmailUsuario(usuario);

        String assuntoEmail = UtilString.EMPTY;

        if (UtilString.isEmpty(assunto)) {
            assuntoEmail = MSG_ASSUNTO_REQUISICAO;
        }else{
            assuntoEmail = assunto;  
        }

        emailText = emailText.replace(TEMPLATE_SAUDACAO, saudacao);
        emailText = emailText.replace(TEMPLATE_MENSAGEM, corpo);
        sendRegistrationEmail(email, assuntoEmail, emailText);
        enviarEmailAdministradores(requests, mensagemAdm, assuntoEmail);
    }

    private String montaCorpoEmailRequisicao(Requests requests, String mensagem) {
        Construction obra = requests.getConstruction();
        String corpo = mensagem.replace(NM_OBRA, obra.getNmObra());
        String detalhesObra = montaDetalhesObra(obra);
        String listaPatrimonios = listarPatrimoniosRequisicao(requests.getPatrimonies());

        StringBuilder retorno = new StringBuilder();
        retorno.append(corpo).append(detalhesObra).append(listaPatrimonios);

        return retorno.toString();
    }

    private String montaDetalhesObra(Construction obra) {
        String nome = UtilString.isNotEmpty(obra.getNmObra()) ? obra.getNmObra() : UtilString.EMPTY;
        String nmCliente = UtilString.isNotEmpty(obra.getNmCliente()) ? obra.getNmCliente() : UtilString.EMPTY;
        String nrDocumento = UtilString.isNotEmpty(obra.getNrCnpjCpf()) ? obra.getNrCnpjCpf() : UtilString.EMPTY;
        String endereco = UtilString.isNotEmpty(obtemEnderecoObraCompleto(obra)) ? obtemEnderecoObraCompleto(obra)
                : UtilString.EMPTY;
        String dtPrevisaoConclusao = UtilObjeto.isNotEmpty(obra.getDtPrevisaoConclusao())
                ? UtilData.toString(obra.getDtPrevisaoConclusao(), UtilData.FORMATO_DDMMAA)
                : UtilString.EMPTY;

        StringBuilder texto = new StringBuilder();
        if (UtilString.isNotEmpty(nome))
            texto.append(ITEM_LISTA.replace(TEXTO, "<b>Obra: </b>" + nome + UtilString.PONTO));
        if (UtilString.isNotEmpty(nmCliente))
            texto.append(ITEM_LISTA.replace(TEXTO, "<b>Cliente: </b>" + nmCliente + UtilString.PONTO));
        if (UtilString.isNotEmpty(nrDocumento))
            texto.append(ITEM_LISTA.replace(TEXTO, "<b>Documento: </b>" + nrDocumento + UtilString.PONTO));
        if (UtilString.isNotEmpty(endereco))
            texto.append(ITEM_LISTA.replace(TEXTO, "<b>Endereço: </b>" + endereco + UtilString.PONTO));
        if (UtilString.isNotEmpty(dtPrevisaoConclusao))
            texto.append(ITEM_LISTA.replace(TEXTO,
                    "<b>Data Prevista Para Conclusão: </b>" + dtPrevisaoConclusao + UtilString.PONTO));

        String retorno = LISTA_NAO_ORDENADA.replace(CONTEUDO, texto.toString());

        return retorno;
    }

    private String obtemEnderecoObraCompleto(Construction obra) {
        StringBuilder retorno = new StringBuilder();

        if (UtilString.isNotEmpty(obra.getNmLogradouro()))
            retorno.append(obra.getNmLogradouro()).append(UtilString.ESPACO);
        if (UtilString.isNotEmpty(obra.getNrNumero()))
            retorno.append(obra.getNrNumero()).append(UtilString.VIRGULA);
        if (UtilString.isNotEmpty(obra.getNmComplemento()))
            retorno.append(obra.getNmComplemento()).append(UtilString.PONTO);
        if (UtilString.isNotEmpty(obra.getNmBairro()))
            retorno.append(obra.getNmBairro()).append(UtilString.PONTO);
        if (UtilString.isNotEmpty(obra.getNmCidade()) && UtilString.isNotEmpty(obra.getNmUf()))
            retorno.append(obra.getNmCidade()).append(UtilString.TRACO).append(obra.getNmUf());

        return retorno.toString();
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
        String motivo = UtilObjeto.isNotEmpty(maintenance.getDsMotivoManutencao()) ? maintenance.getDsMotivoManutencao()
                : UtilString.EMPTY;
        String descricao = UtilObjeto.isNotEmpty(maintenance.getDsObservacao()) ? maintenance.getDsObservacao()
                : UtilString.EMPTY;
        String valor = UtilObjeto.isNotEmpty(maintenance.getVlManutencao())
                ? UtilCast.toString(maintenance.getVlManutencao())
                : UtilString.EMPTY;
        String nmFornecedor = maintenance.getFornecedor().getNmName();

        mensagem = mensagem.replaceAll(NM_PATRIMONIO, nmPatriminio);
        StringBuilder detalhes = new StringBuilder();

        if (UtilString.isNotEmpty(nmPatriminio))
            detalhes.append(ITEM_LISTA.replace(TEXTO, "<b>Patrimônio: </b>" + nmPatriminio) + UtilString.PONTO);

        if (UtilString.isNotEmpty(dtPrevisao))
            detalhes.append(
                    ITEM_LISTA.replace(TEXTO, "<b>Data de Previsão Manutenção: </b>" + dtPrevisao + UtilString.PONTO));

        if (UtilString.isNotEmpty(dtInicio))
            detalhes.append(
                    ITEM_LISTA.replace(TEXTO, "<b>Data Início da Manutenção: </b>" + dtInicio + UtilString.PONTO));

        if (UtilString.isNotEmpty(dtFim))
            detalhes.append(ITEM_LISTA.replace(TEXTO, "<b>Data Fim da Manutenção: </b>" + dtFim + UtilString.PONTO));

        if (UtilString.isNotEmpty(nmFornecedor))
            detalhes.append(ITEM_LISTA.replace(TEXTO, "<b>Forncedor: </b>" + nmFornecedor + UtilString.PONTO));

        if (UtilString.isNotEmpty(tpManutencao))
            detalhes.append(ITEM_LISTA.replace(TEXTO, "<b>Tipo de Manutenção: </b>" + tpManutencao + UtilString.PONTO));

        if (UtilString.isNotEmpty(valor) && !valor.equalsIgnoreCase("0.0"))
            detalhes.append(ITEM_LISTA.replace(TEXTO, "<b>Valor da Manutenção:</b> " + valor + UtilString.PONTO));

        if (UtilString.isNotEmpty(motivo))
            detalhes.append(ITEM_LISTA.replace(TEXTO, "<b>Motivo:</b> " + motivo + UtilString.PONTO));

        if (UtilString.isNotEmpty(descricao))
            detalhes.append(ITEM_LISTA.replace(TEXTO, "<b>Observações:</b> " + descricao + UtilString.PONTO));

        String detalhesPatrimonio = LISTA_NAO_ORDENADA.replace(CONTEUDO, detalhes.toString());
        mensagem = mensagem.concat(detalhesPatrimonio);

        return mensagem;
    }

    private void enviarEmailAdministradores(Requests requests, String mensagem, String assunto) {

        List<User> administradores = userRepository.findByTypeUser(TypeUser.ADMIN);
        Construction obra = requests.getConstruction();
        String corpo = mensagem.replace(NM_OBRA, obra.getNmObra());
        String detalhesObra = montaDetalhesObra(obra);
        String listaPatrimonios = listarPatrimoniosRequisicao(requests.getPatrimonies());

        StringBuilder retorno = new StringBuilder();
        retorno.append(corpo).append(detalhesObra).append(listaPatrimonios);

        for (User administrador : administradores) {
            enviarEmailAdministrador(administrador, TEMPLATE_EMAIL, assunto, retorno.toString());
        }

    }

    private void enviarEmailAdministrador(User administrador, String template, String assunto, String mensagem) {
        String emailText = template;
        String saudacao = montarTitulo(administrador);
        String email = obtemEmailUsuario(administrador);

        emailText = emailText.replace(TEMPLATE_SAUDACAO, saudacao);
        emailText = emailText.replace(TEMPLATE_MENSAGEM, mensagem);

        sendRegistrationEmail(email, assunto, emailText);
    }

    private String montarTitulo(User usuario) {
        String titulo = TITULO_NOVO_USUARIO;
        titulo = titulo.replace("NM_USUARIO", usuario.getNmName());
        return titulo;
    }

    private String montaMensagemNovoUsuario(String senha, String mensagem) {
        mensagem = mensagem.replace("SENHA_ACESSO", "<b>" + senha + "</b>");

        return mensagem;
    }

    private String montaMensagemNovaAlocacao(Allocation allocation, String mensagem) {
        Department departamento = allocation.getDepartamento();

        StringBuilder retorno = new StringBuilder();
        retorno.append(PARAGRAFO.replace(TEXTO, mensagem.replace(NM_DEPARTAMENTO, departamento.getNmDepartamento())));
        retorno.append(listarPatrimonios(obtemListaPatrimonios(allocation)));

        return retorno.toString();

    }

    private String montaMensagemDevolucaoAlocacao(String patrimonios) {
        StringBuilder retorno = new StringBuilder();

        retorno.append(PARAGRAFO.replace(TEXTO, MSG_DEVOLVER_ALOCACAO));
        retorno.append(patrimonios);

        return retorno.toString();

    }

    private String listarPatrimonios(List<Patrimony> patrimonies) {
        StringBuilder retorno = new StringBuilder();
        for (Patrimony patrimony : patrimonies) {
            retorno.append(ITEM_LISTA_COM_BORDA.replace(TEXTO,
                    patrimony.getId() + UtilString.TRACO + patrimony.getNmPatrimonio()));
        }

        String texto = LISTA_NAO_ORDENADA.replace(CONTEUDO, retorno.toString());
        return texto;
    }

    private String listarPatrimoniosRequisicao(List<RequestPatrimony> requestPatrimonies) {
        StringBuilder conteudo = new StringBuilder();
        for (RequestPatrimony requestPatrimony : requestPatrimonies) {
            String div = DIV.replace(CONTEUDO, criaItemRequisicao(requestPatrimony));
            conteudo.append(ITEM_LISTA_COM_BORDA.replace(TEXTO, div));
        }
        String retorno = LISTA_NAO_ORDENADA.replace(CONTEUDO, conteudo.toString());

        return retorno;
    }

    private String criaItemRequisicao(RequestPatrimony requestPatrimony) {
        String dataPrevisaoRetirada = UtilObjeto.isNotEmpty(requestPatrimony.getDtPrevisaoRetirada())
                ? UtilData.toString(requestPatrimony.getDtPrevisaoRetirada(), UtilData.FORMATO_DDMMAA)
                : UtilString.EMPTY;
        String dataRetirada = UtilObjeto.isNotEmpty(requestPatrimony.getDtRetirada())
                ? UtilData.toString(requestPatrimony.getDtRetirada(), UtilData.FORMATO_DDMMAA)
                : UtilString.EMPTY;
        String dataDevolucao = UtilObjeto.isNotEmpty(requestPatrimony.getDtDevolucao())
                ? UtilData.toString(requestPatrimony.getDtDevolucao(), UtilData.FORMATO_DDMMAA)
                : UtilString.EMPTY;
        String nmPatrimonio = requestPatrimony.getPatrimony().getNmPatrimonio();

        StringBuilder retorno = new StringBuilder();
        if (UtilString.isNotEmpty(nmPatrimonio))
            retorno.append(SPAN.replace(CONTEUDO, "<b>Patrimonio:</b> " + nmPatrimonio)).append("<br/>");
        if (UtilString.isNotEmpty(dataPrevisaoRetirada))
            retorno.append(SPAN.replace(CONTEUDO, "<b>Previsão de Retirada:</b> " + dataPrevisaoRetirada))
                    .append("<br/>");
        if (UtilString.isNotEmpty(dataRetirada))
            retorno.append(SPAN.replace(CONTEUDO, "<b>Data de Retirada:</b> " + dataRetirada)).append("<br/>");
        if (UtilString.isNotEmpty(dataDevolucao))
            retorno.append(SPAN.replace(CONTEUDO, "<b>Data de Devolução:</b> " + dataDevolucao)).append("<br/>");

        return retorno.toString();

    }

    private String obtemEmailUsuario(User usuario) {
        Contacts contato = usuario.getContacts().stream().filter(c -> TypeContacts.EMAIL.equals(c.getTypeContacts()))
                .findFirst().orElseThrow(() -> new IllegalRequestException("E-mail inválido!"));
        return contato.getDsContato();
    }

    private List<Patrimony> obtemListaPatrimonios(Allocation allocation) {
        List<AllocationPatrimony> aps = allocation.getPatrimonios();
        List<Patrimony> patrimonies = new ArrayList<>();

        for (AllocationPatrimony allocationPatrimony : aps) {
            patrimonies.add(allocationPatrimony.getPatrimony());
        }
        return patrimonies;

    }

    @Override
    public void enviarEmailRequisicoesGestor(Map<String, Object> dados, String mensagem, String assunto) {
        User usuario = (User) dados.get("USUARIO");
        String emailText = TEMPLATE_EMAIL;
        String saudacao = montarTitulo(usuario);
        String corpo = montaCorpoEmailRequisicaoGestor(dados, mensagem);
        String email = obtemEmailUsuario(usuario);

        emailText = emailText.replace(TEMPLATE_SAUDACAO, saudacao);
        emailText = emailText.replace(TEMPLATE_MENSAGEM, corpo);
        sendRegistrationEmail(email, assunto, emailText);
    }

    private String montaCorpoEmailRequisicaoGestor(Map<String, Object> dados, String mensagem) {
        String corpo = mensagem;       
        List<RequestPatrimony> rp = UtilObjeto.isNotEmpty(dados.get("PATRIMONIOS")) ? (List<RequestPatrimony>) dados.get("PATRIMONIOS") : new ArrayList<>() ; 
        String listaPatrimonios = listarPatrimoniosRequisicaoGestor(rp);

        StringBuilder retorno = new StringBuilder();
        retorno.append(corpo).append(listaPatrimonios);

        return retorno.toString();
    }

     private String listarPatrimoniosRequisicaoGestor(List<RequestPatrimony> requests) {
        StringBuilder conteudo = new StringBuilder();
        List<RequestPatrimony> requestPatrimonies = requests;
        conteudo.append(TABLE_TR_TH.replace(TR, montaHeaderTableRequisicoesGestor()));
        for (RequestPatrimony requestPatrimony : requestPatrimonies) {
            String tr = TABLE_TR.replace(TR, montaLinhaTabelaRequisicaoGestor(requestPatrimony.getRequests().getConstruction(), requestPatrimony));
            conteudo.append(tr);
        }
        String retorno = "<br /> <br />".concat(TABLE.replace(CONTEUDO, conteudo.toString()));

        return retorno;
    }
    private String montaLinhaTabelaRequisicaoGestor(Construction construction, RequestPatrimony requestPatrimony){
        StringBuilder retorno = new StringBuilder();

        retorno.append(TABLE_TD.replace(TD, requestPatrimony.getPatrimony().getNmPatrimonio()));
        retorno.append(TABLE_TD.replace(TD, construction.getNmObra()));
        retorno.append(TABLE_TD.replace(TD, obtemEnderecoObraCompleto(construction)));
        retorno.append(TABLE_TD.replace(TD, UtilData.toString(requestPatrimony.getDtRetirada(), UtilData.FORMATO_DDMMAA)));
        retorno.append(TABLE_TD.replace(TD, UtilData.toString(requestPatrimony.getDtPrevisaoDevolucao(), UtilData.FORMATO_DDMMAA)));
    
        return retorno.toString();
    }

    private String montaHeaderTableRequisicoesGestor(){
        StringBuilder retorno = new StringBuilder();

        retorno.append(TABLE_TH.replace(TH, "Patrimônio"));
        retorno.append(TABLE_TH.replace(TH, "Obra"));
        retorno.append(TABLE_TH.replace(TH, "Endereço"));
        retorno.append(TABLE_TH.replace(TH, "Retirada"));
        retorno.append(TABLE_TH.replace(TH, "Previsão Devolução"));

        return retorno.toString();
    }


    public void enviarEmailAdminJobPatrimonio(Map<String, Object> dados, String mensagem, String assunto){
        List<User> administradores = userRepository.findByTypeUser(TypeUser.ADMIN);
        String emailText = TEMPLATE_EMAIL;
        String corpo = montaCorpoEmailRequisicaoAdmin(dados, mensagem);

        for (User administrador : administradores) {
            enviarEmailAdministrador(administrador, emailText, assunto, corpo);
        }
    }

    private String montaCorpoEmailRequisicaoAdmin(Map<String, Object> dados, String mensagem) {
        StringBuilder corpo = new StringBuilder();
        corpo.append(mensagem);
        corpo.append("<br /><br />");
        corpo.append(STRING_H3.replace(H3, "Patrimônios Com Entrega Atrasada"));
        corpo.append("<br />");       
        List<RequestPatrimony> rpVencidos = UtilObjeto.isNotEmpty(dados.get("VENCIDOS")) ? (List<RequestPatrimony>) dados.get("VENCIDOS") : new ArrayList<>() ; 
        String listaPatrimoniosVencidos = listarPatrimoniosRequisicaoAdmin(rpVencidos);
        corpo.append(listaPatrimoniosVencidos);
        corpo.append("<br />");
        corpo.append(STRING_H3.replace(H3, "Patrimônios Próximo Da Entrega")); 
        corpo.append("<br />");      
        List<RequestPatrimony> rpVencer = UtilObjeto.isNotEmpty(dados.get("VENCER")) ? (List<RequestPatrimony>) dados.get("VENCER") : new ArrayList<>() ; 
        String listaPatrimoniosVencer = listarPatrimoniosRequisicaoAdmin(rpVencer);
        corpo.append(listaPatrimoniosVencer);
        corpo.append("<br />");


        return corpo.toString();
    }

    private String listarPatrimoniosRequisicaoAdmin(List<RequestPatrimony> requests) {
        StringBuilder conteudo = new StringBuilder();
        List<RequestPatrimony> requestPatrimonies = requests;
        conteudo.append(TABLE_TR_TH.replace(TR, montaHeaderTableRequisicoesAdmin()));
        for (RequestPatrimony requestPatrimony : requestPatrimonies) {
            String tr = TABLE_TR.replace(TR, montaLinhaTabelaRequisicaoAdmin(requestPatrimony));
            conteudo.append(tr);
        }
        String retorno = TABLE.replace(CONTEUDO, conteudo.toString());

        return retorno;

    }

    private String montaHeaderTableRequisicoesAdmin(){
        StringBuilder retorno = new StringBuilder();

        retorno.append(TABLE_TH.replace(TH, "Patrimônio"));
        retorno.append(TABLE_TH.replace(TH, "Obra"));
        retorno.append(TABLE_TH.replace(TH, "Responsável"));
        retorno.append(TABLE_TH.replace(TH, "Contato"));
        retorno.append(TABLE_TH.replace(TH, "Retirada"));
        retorno.append(TABLE_TH.replace(TH, "Previsão Devolução"));

        return retorno.toString();
    }


    private String montaLinhaTabelaRequisicaoAdmin(RequestPatrimony requestPatrimony){
        StringBuilder retorno = new StringBuilder();

        retorno.append(TABLE_TD.replace(TD, requestPatrimony.getPatrimony().getNmPatrimonio()));
        retorno.append(TABLE_TD.replace(TD, requestPatrimony.getRequests().getConstruction().getNmObra()));
        retorno.append(TABLE_TD.replace(TD, requestPatrimony.getRequests().getConstruction().getUser().getNmName()));
        retorno.append(TABLE_TD.replace(TD, listarContatos(requestPatrimony.getRequests().getConstruction().getUser().getContacts())));
        retorno.append(TABLE_TD.replace(TD, UtilData.toString(requestPatrimony.getDtRetirada(), UtilData.FORMATO_DDMMAA)));
        retorno.append(TABLE_TD.replace(TD, UtilData.toString(requestPatrimony.getDtPrevisaoDevolucao(), UtilData.FORMATO_DDMMAA)));
    
        return retorno.toString();
    }

    private String listarContatos(List<Contacts> contatos){
        if (UtilObjeto.isEmpty(contatos))
            return UtilString.EMPTY;
            
        StringBuilder retorno = new StringBuilder();

        for (Contacts c : contatos) {
            retorno.append(c.getDsContato()).append("<br />");
        }

        return retorno.toString();
    }
}
