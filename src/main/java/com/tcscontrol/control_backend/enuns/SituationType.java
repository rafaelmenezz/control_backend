package com.tcscontrol.control_backend.enuns;

public enum SituationType {
    DISPONIVEL("Disponível"),
    ALOCADO("Alocado"),
    REGISTRADO("Registrado"),
    EM_MANUTENCAO("Em Manutenção"),
    FIXO("Fixo");

    private String value;

    private SituationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
