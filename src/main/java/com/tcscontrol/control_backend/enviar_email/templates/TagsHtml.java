package com.tcscontrol.control_backend.enviar_email.templates;

public interface TagsHtml {

    String TEXTO = "TEXTO";
    String CONTEUDO = "CONTEUDO";
    
    String paragrafo = "<p> TEXTO </p>";

    String listNaoOrdenada = "<ul style='list-style: none'> CONTEUDO </ul>";
    String itemLista = "<li>TEXTO</li>";
}
