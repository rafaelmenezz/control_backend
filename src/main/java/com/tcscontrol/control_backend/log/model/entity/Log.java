package com.tcscontrol.control_backend.log.model.entity;

import java.sql.Date;

import com.tcscontrol.control_backend.patrimony.model.Patrimony;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "LOGS")
@AllArgsConstructor
@NoArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_log")
    private Long id;

    @Column(name = "id_patrimonio")
    private Patrimony patrimony;

    @Column(name = "id_usuario")
    private User user;

    @Column(name = "vl_valor_anterior")
    private String valorAnterior;

    @Column(name = "vl_valor_atual")
    private String valorAtual;

    @Column(name = "nm_nome_campo_alterado")
    private String nomeCampoAlterado;

    @Column(name = "dt_data_realizada")
    private Date dataAlteracao;
    
}
