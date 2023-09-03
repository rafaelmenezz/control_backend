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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@SQLDelete(sql = "UPDATE USUARIO SET fl_status = 'Inativo' Where id = ?")
@Table(name = "USUARIO")
@EqualsAndHashCode(callSuper = false)
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

    @Builder
    public User(Long id, String nmName, DocumentoType documentoType, Status status, 
    List<Contacts> contacts, Integer nrMatricula,String nmSenha, String nrCPF, Byte[] ftFoto, TypeUser typeUser) {
        super(id, nmName, documentoType, status, contacts);
        this.nrMatricula = nrMatricula;
        this.nmSenha = nmSenha;
        this.nrCpf = nrCPF;
        this.ftFoto = ftFoto;
        this.typeUser = typeUser;

    }

}
