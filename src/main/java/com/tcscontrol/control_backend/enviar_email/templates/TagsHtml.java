package com.tcscontrol.control_backend.enviar_email.templates;

public interface TagsHtml {

    String TEXTO = "TEXTO";
    String CONTEUDO = "CONTEUDO";
    String TR = "TR";
    String TD = "TD";
    String TH = "TH";
    String H3 = "H3";

    String PARAGRAFO = "<p> TEXTO </p>";
    String DIV = "<div style='padding: 1px'> CONTEUDO </div>";
    String SPAN = "<span> CONTEUDO </span>";

    String TABLE = "<table class='table'> CONTEUDO </table>";
    String TABLE_TR_TH = "<tr style='padding-top: 8px; padding-bottom: 8px;'> TR </tr>";
    String TABLE_TR = "<tr style='border-top: 1px solid #000'> TR </tr>";
    String TABLE_TD = "<td style='padding-top: 8px; padding-bottom: 8px;'> TD </td>";
    String TABLE_TH = "<th>TH</th>";

    String STRING_H3 = "<h3 style='font-color: #CCC'> H3 </h3>"; 

    String LISTA_NAO_ORDENADA = "<ul style='list-style: none; margin-top: 5px'> CONTEUDO </ul>";
    String ITEM_LISTA = "<li style='padding: 1px;'>TEXTO</li>";
    String ITEM_LISTA_COM_BORDA = "<li style='border-left: 5px solid #1c5edb; padding: 1px; padding-left: 5px; margin-top: 2px'>TEXTO</li>";
}
