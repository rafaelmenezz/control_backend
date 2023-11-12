package com.tcscontrol.control_backend.enviar_email.templates;

public interface TemplateEmail {

    String TEMPLATE_SAUDACAO = "{SAUDACAO}";
    String TEMPLATE_MENSAGEM = "{CORPO_MENSAGEM}";

    String TEMPLATE_EMAIL = """

            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Control</title>

                <style>
                    * {
                        margin: 0;
                        padding: 0;
                        box-sizing: border-box;
                    }

                    body {

                        min-height: 100vh;
                        overflow-x: hidden;
                    }

                    .container {
                        width: 100%;
                        min-width: 500px;
                    }

                    .cabecalho {
                        margin: 0 auto;
                        width: 750px;
                        max-height: 250px;
                        background-repeat: no-repeat;
                        background-size: cover;
                        background-position: center;
                    }

                    .imagem {
                        width: 100%;
                    }

                    .imagem img {
                        width: 100%;
                        text-align: center;
                    }

                    .marca {
                        width: 450px;
                        text-align: left;
                        color: #FFFFFF
                    }
                    .msg-corpo {
                        margin: 0 auto;
                        width: 750px;
                        padding-top: 3%;
                        padding-left: 2%;
                        padding-right: 2%;
                        padding-bottom: 3%;
                    }

                    .msg-corpo > ul{
                        list-style: circle;
                        width: 100%;
                        margin-bottom: 3%;
                        margin-left: 3%;
                    }

                    .msg-corpo > ul{
                        width: 100%;
                    }

                    p.saudacao{
                        margin-bottom: 25px;
                    }

                    p.final{
                        margin-top: 25px;
                        margin-bottom: 15px;
                    }
                    .footer {
                        background-color: #f5f1f1;
                        padding: 2%;
                        margin: 0 auto;
                        width: 750px;
                    }

                    .footer > ul{
                        list-style: none;
                    }
                </style>
            </head>

            <body>
                <div class="container">
                    <div class="cabecalho">
                            <img src="cid:banner" alt="Logo Control" />
                    </div>
                    <div class="msg-corpo">
                        <p class='saudacao'>{SAUDACAO}</p>
                        {CORPO_MENSAGEM}
                        <p class="final">Atenciosamente,</p>
                    </div>

                    <div class="footer">
                        <ul>
                            <li><b>EQUIPE CONTROL</b></li>
                            <li>Gerenciador de Controle Patrimonial</li>
                            <li> <a> gcp.tcs.senac2023@gmail.com </a></li>
                            <li>48 93366 1122</li>
                        </ul>
                    </div>
                </div>

            </body>

            </html>

                    """;
}
