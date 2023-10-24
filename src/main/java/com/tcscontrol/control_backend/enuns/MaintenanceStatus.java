package com.tcscontrol.control_backend.enuns;

public enum MaintenanceStatus {
    AGENDADA("Agendada"), 
    CANCELADA("Cancelada"), 
    EM_EXECUCAO("Em Execução");
    
    private String value;

    private MaintenanceStatus(String value){
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
