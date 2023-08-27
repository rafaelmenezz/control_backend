package com.tcscontrol.control_backend.user.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.Length;

import com.tcscontrol.control_backend.contacts.model.Contacts;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.enuns.TypeUser;
import com.tcscontrol.control_backend.enuns.converters.StatusConverter;
import com.tcscontrol.control_backend.enuns.converters.TypeUserConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
//@SQLDelete(sql = "UPDATE USUARIO SET fl_status = 'Inativo' Where idUser = ?")
@Table(name = "USUARIO")
public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long idUser;

    @NotBlank
    @NotNull
    @Column(name="nm_usuario",nullable = false, length = 80 )
    private String nmUsuario;

    @Column(name="nr_matricula",nullable = true)
    private Integer nrMatricula;

    // @Length(min = 6, max = 20)
    @Column(name = "nm_senha", nullable = true, length = 100 )
    private String nmSenha;

    @NotNull
    @NotBlank
    @Length(min=14, max = 20)
    @Column(name = "nr_cpf", nullable = false,  length = 20)
    private String nrCpf;

    @Column(length = 15, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status flStatus = Status.ACTIVE;

    @Lob
    @Column(name = "ft_foto")
    private Byte[] ftFoto;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<Contacts> contacts = new ArrayList<>();

    @NotNull
    @Column(name= "tipo_usuario", length = 15)
    @Convert(converter = TypeUserConverter.class)
    private TypeUser typeUser = TypeUser.PEAO;

}
