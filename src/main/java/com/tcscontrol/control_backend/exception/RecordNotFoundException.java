package com.tcscontrol.control_backend.exception;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(Long id){
        super("Registro não encontrado com o id: " + id);
    }

    public RecordNotFoundException(String mensagem ,Long id){
        super(mensagem + " id: " + id);
    }

    public RecordNotFoundException(String mensagem){
        super(mensagem);
    }
    
}
