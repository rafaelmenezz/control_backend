package com.tcscontrol.control_backend.constructions.model.entity;

import java.sql.Date;

import org.springframework.data.annotation.Id;

import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OBRAS")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Constructions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_obra")
    private Long id;

    @Column(name = "nm_obra")
    private String nmObra;
    
    //@Column(name = "nm_observacao")
    //private String Observacao;
    
    @Column(name = "nm_cnpj_cpf")
    private String nmCnpjCpf;
    
    @Column(name = "nm_cliente")
    private String nmCliente;
    
    @Column(name = "nm_cep")
    private String nmCep;
    
    @Column(name = "nm_logradouro")
    private String nmLogradouro;
    
    @Column(name = "nm_numero")
    private String nmNumero;
    
    @Column(name = "nm_complemento")
    private String nmComplemento;
    
    @Column(name = "nm_bairro")
    private String nmBairro;
    
    @Column(name = "nm_cidade")
    private String nmCidade;
    
    @Column(name = "nm_estado")
    private String nmEstado;
    
    @Column(name = "id_usuario_responsavel")
    private User user;
    
    @Column(name = "dt_inicio")
    private Date dtInicio;
    
    @Column(name = "dt_prev_conclusao")
    private Date dtPrevConclusao;
    
    @Column(name = "dt_fim")
    private Date dtFim;
    
    
}
