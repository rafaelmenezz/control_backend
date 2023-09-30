package com.tcscontrol.control_backend.maintenance.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tcscontrol.control_backend.enuns.TypeMaintenance;
import com.tcscontrol.control_backend.enuns.converters.TypeUserConverter;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.pessoa.fornecedor.Fornecedor;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "MANUTENCOES")
@AllArgsConstructor
@NoArgsConstructor
public class Maintenance implements Serializable {

      @Serial
      private static final long serialVersionUID = 1L;

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      @Column(name = "id_manutencao")
      private Long id;

      @NotNull
      @Column(name = "tp_manutencao", length = 15)
      @Convert(converter = TypeUserConverter.class)
      private TypeMaintenance tpManutencao;

      @Column(name = "ds_motivo_manutencao")
      private String dsMotivoManutencao;

      @Column(name = "vl_manutencao")
      private Double vlManutencao;

      @Column(name = "ds_observacao")
      private String dsObservacao;

      @Column(name = "dt_agendamento")
      private Date dtAgendamento;

      @Column(name = "dt_entrada")
      private Date dtEntrada;

      @Column(name = "dt_fim")
      private Date dtFim;

      @ManyToOne(fetch = FetchType.EAGER, optional = false)
      @JoinColumn(name = "id_patrimonio", nullable = false)
      @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
      private Patrimony patrimony;

      @ManyToOne(fetch = FetchType.EAGER, optional = false)
      @JoinColumn(name = "id_fornecedor", nullable = false)
      @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
      private Fornecedor fornecedor;

}
