package com.tcscontrol.control_backend.pessoa.user.model.entity;

import java.util.List;

import com.tcscontrol.control_backend.contacts.model.Contacts;
import com.tcscontrol.control_backend.enuns.DocumentoType;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.enuns.TypeUser;
import com.tcscontrol.control_backend.enuns.converters.TypeUserConverter;
import com.tcscontrol.control_backend.pessoa.Pessoa;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//@SQLDelete(sql = "UPDATE USUARIO SET fl_status = 'Inativo' Where idUser = ?")
@Table(name = "USUARIO")
public class User extends Pessoa {

    @Column(name="nr_matricula")
    private Integer nrMatricula;

    @Column(name = "nm_senha")
    private String nmSenha;

    @Column(name = "nr_cpf")
    private String nrCpf;

    @Lob
    @Column(name = "ft_foto")
    private Byte[] ftFoto;

    @NotNull
    @Column(name= "tp_usuario", length = 15)
    @Convert(converter = TypeUserConverter.class)
    private TypeUser typeUser = TypeUser.PEAO;

    public User(){super();}

    public User(Long id, String nmName, Integer nrMatricula, String nmSenha, String nrCPF, Byte[] ftFoto,
    TypeUser tyUser, List<Contacts> contacts){
        super();
        super.setIdPessoa(id);
        super.setNmName(nmName);
        super.setTypeDocumento(DocumentoType.CPF);
        super.setTpStatus(Status.ACTIVE);
        super.setContacts(contacts);
        this.nrCpf = nrCPF;
        this.nrMatricula = nrMatricula;
        this.nmSenha = nmSenha;
        this.ftFoto = ftFoto;
        this.typeUser = tyUser;
    }

    public void setNmUsuario(String name){
         super.setNmName(name);
    }

    public String getNmUsuario(){
        return super.getNmName();
    }

    public void setIdUser(Long idUser){
        super.setIdPessoa(idUser);
    }

    public Long getIdUser(){
        return super.getIdPessoa();
    }

    public void setTpDocumento(DocumentoType documentoType){
        super.setTypeDocumento(documentoType);
    }

    public DocumentoType getDocumentoType(){
        return super.getTypeDocumento();
    }

    public void setContacts(List<Contacts> contacts){
        super.setContacts(contacts);
    }

    public List<Contacts> getContacts(){
        return super.getContacts();
    }
    public void setStatus(Status status){
        super.setTpStatus(status);
    }
    public Status getStatus(){
        return super.getTpStatus();
    }

}
