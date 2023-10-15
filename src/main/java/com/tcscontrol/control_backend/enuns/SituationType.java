package com.tcscontrol.control_backend.enuns;

public enum SituationType {
    LIVRE("Livre"), ALOCADO("Alocado"), REGISTRADO("Registrado"), EM_MANUTENCAO("Manutenção");

    private String value;

    private SituationType(String value){
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
