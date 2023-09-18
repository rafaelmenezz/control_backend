package com.tcscontrol.control_backend.allocation.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tcscontrol.control_backend.department.model.entity.Department;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "ALOCACOES")
@AllArgsConstructor
@NoArgsConstructor
public class Allocation implements Serializable {
      
      @Serial
      private static final long serialVersionUID = 1L;

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      private Long id;

      @OneToOne
      private Allocation parent;

      @Column(name = "dt_alocacao")
      private Date  dtAlocacao;

      @Column(name = "dt_devolucao")
      private Date  dtDevolucao;

      @Column(name = "nm_observacao")
      private String nmObservacao;

      @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "departamento_patrimonio", 
	joinColumns = @JoinColumn(name = "patrimonio_id"),
	inverseJoinColumns = @JoinColumn(name = "alocacao_id"))
      private Set<Patrimony> patrimonios = new HashSet<>();

      @ManyToOne(fetch = FetchType.EAGER)
      @JoinColumn(name = "id_departamento", nullable = false)
      @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
      private Department departamento;
}
