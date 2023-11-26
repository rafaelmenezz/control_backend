package com.tcscontrol.control_backend.enuns;

public enum RelatoryType {
    BENS_BAIXADOS("Bens Baixados"),
    DEVOLUCOES_VENCIDAS("Devoluções Vencidas"),
    PATRIMONIOS_DISPONIVEIS("Patrimônio Disponiveis"),
    GASTOS_MANUTENCAO("Gastos com Manutenção"),
    GERAR_QRCODE("Gerar Qr Code Patrimônio");

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
