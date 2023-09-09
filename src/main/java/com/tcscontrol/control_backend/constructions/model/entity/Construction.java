package com.tcscontrol.control_backend.constructions.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "OBRAS")
@AllArgsConstructor
@NoArgsConstructor
public class Construction implements Serializable {

      @Serial
      private static final long serialVersionUID = 1L;

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      @Column(name = "id_obra")
      private long id;

      @Column(name = "nm_obra")
      private String nmObra;

      @Column(name = "nr_cpf_cnpj")
      private String nrCnpjCpf;

      @Column(name = "nm_cliente")
      private String nmCliente;

      @Column(name = "nr_cep")
      private String nrCep;

      @Column(name = "nm_logradouro")
      private String nmLogradouro;

      @Column(name = "nr_numero")
      private Integer nrNumero;

      @Column(name = "nm_cidade")
      private String nmCidade;

      @Column(name = "nm_uf")
      private String nmUf;

      @Column(name = "ds_observacao")
      private String dsObservacao;

      @Column(name = "dt_inicio")
      private Date dtInicio;

      @Column(name = "dt_previsao_conclusao")
      private Date dtPrevisaoConclusao;

      @Column(name = "dt_fim")
      private Date dtFim;

      @ManyToOne(fetch = FetchType.EAGER, optional = false)
      @JoinColumn(name = "id_usuario", nullable = false)
      @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
      private User user;

}
