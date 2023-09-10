package com.tcscontrol.control_backend.warranty.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "GARANTIAS")
public class Warranty implements Serializable {

      @Serial
      private static final long serialVersionUID = 1L;

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      @Column(name = "id_garantia")
      private Long id;

      @Column(name = "ds_garantia")
      private String dsGarantia;

      @Column(name = "dt_validade")
      private Date dtValidade;

      @ManyToOne(fetch = FetchType.LAZY, optional = false)
      @JoinColumn(name = "id_patrimonio", nullable = false)
      @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
      private Patrimony patrimony;
}