package com.tcscontrol.control_backend.patrimonyconstruction.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tcscontrol.control_backend.constructions.model.entity.Construction;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Equipamento")
@AllArgsConstructor
@NoArgsConstructor
public class Requests implements Serializable{
      
      @Serial
      private static final long serialVersionUID = 1L;

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      private Long id;

      @Column(name = "dt_solicitacao")
      private Date dtSolicitacao;

      @Column(name = "dt_inicio")
      private Date dtInicio;

      @Column(name = "dt_devolucao")
      private Date dtDevolcao;

      @OneToMany(orphanRemoval = false)
      private List<Patrimony> patrimonios;

      @ManyToOne(fetch = FetchType.EAGER, optional = false)
      @JoinColumn(name = "id_obras", nullable = false)
      @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
      private Construction construction;
}
