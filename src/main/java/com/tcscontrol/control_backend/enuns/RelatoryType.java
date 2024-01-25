package com.tcscontrol.control_backend.enuns;

public enum RelatoryType {
    BENS_BAIXADOS("Bens Baixados"),
    DEVOLUCOES_VENCIDAS("Devoluções Vencidas"),
    PATRIMONIOS_DISPONIVEIS("Patrimônio Disponiveis"),
    GASTOS_MANUTENCAO("Gastos com Manutenção"),
    GERAR_QRCODE("Gerar Qr Code Patrimônio"),
    GERAL_INVENTARIO("Geral Inventário"),
    GERAL_PATRIMONIO("Geral Patrimônio"),
    MANUTENCAO_AGENDADA("Manutenções Agendadas"),
    PATRIMONIO_NAS_OBRAS("Patrimônios Nas Obras"),
    PATRIMONIO_NOS_DEPTOS("Patrimônios Nos Departamentos"),
    PATRIMONIO_POR_OBRA("Patrimônios Por Obras"),
    REQUISICOES_PENDENTES("Requisições Pendentes");

    private String value;

    private RelatoryType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    @Override
    public String toString(){
        return value;
    }
}
