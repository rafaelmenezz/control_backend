/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcscontrol.control_backend.utilitarios;

import com.tcscontrol.control_backend.enuns.DocumentoType;
import com.tcscontrol.control_backend.enuns.SituationType;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.enuns.TypeContacts;
import com.tcscontrol.control_backend.enuns.TypeMaintenance;
import com.tcscontrol.control_backend.enuns.TypeUser;
import com.tcscontrol.control_backend.enuns.TypeWarranty;

public class UtilControl {

    public static String gerarNumero(int qtde) {
        String numeroGerado = "";
        int indice;
        for (int i = 0; i < qtde; i++) {
            indice = (int) (Math.random() * 10);
            numeroGerado += indice;
        }
        return numeroGerado;
    }

    public static String gerarCpf() {
        return gerarNumero(3) + "." + gerarNumero(3) + "." + gerarNumero(3) + "-" + gerarNumero(2);
    }

    public static String gerarCnpj() {
        return gerarNumero(2) + "." + gerarNumero(3) + "." + gerarNumero(3) + "/" + gerarNumero(4)
                + "-" + gerarNumero(2);
    }

    public static String gerarTelefoneFixo() {
        return "(48)3" + gerarNumero(3) + "-" + gerarNumero(4);
    }

    public static String gerarCelular() {
        return "(48)9" + gerarNumero(4) + "-" + gerarNumero(4);
    }

    public static String gerarCep() {
        return gerarNumero(5) + "-" + gerarNumero(3);
    }

    public static String gerarNome() {
        String[] nomes = { "Junior", "Marcos", "Ana", "Maria", "Silvio", "Suelen", "Joana", "Mateus",
                "Lúcio", "João", "Leandro", "Soeli" };
        int indice = (int) (Math.random() * nomes.length);
        return nomes[indice] + " " + gerarSobrenome();
    }

    public static String gerarProfissao() {
        String[] profissoes = { "Programador", "Analista", "Pintor", "Costureiro",
                "Padeiro", "Eletricista", "Consultor", "Predeiro",
                "Carpinteiro", "Professor", "Testador", "Pescador" };
        int indice = (int) (Math.random() * profissoes.length);
        return profissoes[indice];
    }

    public static String gerarBandeira() {
        String[] bandeiras = { "Master-Card", "Visa", "Danners", "Amex" };
        int indice = (int) (Math.random() * bandeiras.length);
        return bandeiras[indice];
    }

    private static String gerarSobrenome() {
        String[] sobrenomes = { "Pereira", "Oliveira", "Antunes", "da Silva", "Santos", "Rocha", "Moura",
                "Dias", "Mendes", "Albino", "Dutra", "Mendonça" };
        int indice = (int) (Math.random() * sobrenomes.length);
        return sobrenomes[indice];
    }

    public static String gerarCidade() {
        String[] cidades = { "São José", "Palhoça", "Florianópolis", "Criciuma", "Chapecó", "Curitiba",
                "Porto Alegre", "São Paulo", "Máceio", "Biguaçú", "Belo Horizonte", "Pinhais" };
        int indice = (int) (Math.random() * cidades.length);
        return cidades[indice];
    }

    public static String gerarLogin() {
        String nome = gerarNome();
        return nome.toLowerCase() + "@";
    }

    public static String gerarSenha(int qtde) {
        String[] letras = { "a", "b", "c", "d", "e", "f", "g", "h", "i",
                "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D",
                "E",
                "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "0",
                "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        int indice;
        String senha = "";
        for (int i = 0; i < qtde; i++) {
            indice = (int) (Math.random() * letras.length);
            senha += letras[indice];
        }
        return senha;
    }

    public static void main(String[] args) {
        System.out.println("Cidade: " + gerarCidade());
    }

    public static Status convertStatusValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Ativo" -> Status.ACTIVE;
            case "Inativo" -> Status.INACTIVE;
            default -> throw new IllegalArgumentException("Status do usuário inválido.");
        };
    }

    public static TypeMaintenance convertTypeMaintenanceValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Preventiva" -> TypeMaintenance.PREVENTIVA;
            case "Corretiva" -> TypeMaintenance.CORRETIVA;
            default -> throw new IllegalArgumentException("Tipo de manutenção inválido!");
        };
    }

    public static TypeWarranty convertTypeWarrantyValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Legal" -> TypeWarranty.LEGAL;
            case "Contratual" -> TypeWarranty.CONTRATUAL;
            case "Estendida" -> TypeWarranty.ESTENDIDA;
            default -> throw new IllegalArgumentException("Tipo de garantia inválida!");
        };
    }

    public static SituationType convertSituationTypeValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Disponível" -> SituationType.DISPONIVEL;
            case "Fixo" -> SituationType.FIXO;
            case "Alocado" -> SituationType.ALOCADO;
            case "Registrado" -> SituationType.REGISTRADO;
            case "Em Manutenção" -> SituationType.EM_MANUTENCAO;
            default -> throw new IllegalArgumentException("Situação do património inválida!");
        };
    }

    public static TypeUser convertTypeUserValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Admin" -> TypeUser.ADMIN;
            case "Gestor" -> TypeUser.GESTOR;
            case "Requisitante" -> TypeUser.REQUISITANTE;
            default -> throw new IllegalArgumentException("Tipo de Usuário inválido.");
        };
    }

    public static TypeContacts convertTypeContactsValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Telefone" -> TypeContacts.TELEFONE;
            case "Celular" -> TypeContacts.CELULAR;
            case "E-mail" -> TypeContacts.EMAIL;
            case "WhatsApp" -> TypeContacts.WHATSAPP;
            case "Instagran" -> TypeContacts.INSTAGRAN;
            default -> throw new IllegalArgumentException("Tipo de Usuário inválido.");
        };
    }

    public static DocumentoType convertDocumentoValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "CPF" -> DocumentoType.CPF;
            case "CNPJ" -> DocumentoType.CNPJ;
            default -> throw new IllegalArgumentException("Documento do usuário inválido.");
        };
    }
}
