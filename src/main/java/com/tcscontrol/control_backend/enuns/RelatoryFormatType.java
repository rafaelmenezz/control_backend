package com.tcscontrol.control_backend.enuns;

public enum RelatoryFormatType {
    EXCEL("EXCEL"),
    PDF("PDF");

    private String value;

    private RelatoryFormatType(String value){
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
