package com.tcscontrol.control_backend.enuns;

public enum TypeMaintenance {
      PREVENTIVA("Preventiva"),
      CORRETIVA("Corretiva");

      private String value;

      private TypeMaintenance(String value){
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
