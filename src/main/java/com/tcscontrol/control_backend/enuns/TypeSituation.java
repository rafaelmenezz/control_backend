package com.tcscontrol.control_backend.enuns;

public enum TypeSituation {
    DISPONIVEL("Disponivel"), ALOCADO("Alocado"), REGISTRADO("Registrado"), EM_MANUTENCAO("Manutenção");

    private String value;

    private TypeSituation(String value){
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
