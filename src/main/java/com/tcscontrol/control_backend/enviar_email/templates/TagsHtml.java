package com.tcscontrol.control_backend.enviar_email.templates;

public interface TagsHtml {

    String TEXTO = "TEXTO";
    String CONTEUDO = "CONTEUDO";
    String PARAGRAFO = "<p> TEXTO </p>";
    String DIV = "<div style='padding: 1px'> CONTEUDO </div>";
    String SPAN = "<span> CONTEUDO </span>";

    String LISTA_NAO_ORDENADA = "<ul style='list-style: none; margin-top: 5px'> CONTEUDO </ul>";
    String ITEM_LISTA = "<li style='padding: 1px;'>TEXTO</li>";
    String ITEM_LISTA_COM_BORDA = "<li style='border-left: 5px solid #1c5edb; padding: 1px; margin-top: 2px'>TEXTO</li>";
}
