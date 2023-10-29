package com.tcscontrol.control_backend.maintenance.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tcscontrol.control_backend.enuns.MaintenanceStatus;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.enuns.TypeMaintenance;
import com.tcscontrol.control_backend.enuns.converters.MaintenanceStatusConverter;
import com.tcscontrol.control_backend.enuns.converters.StatusConverter;
import com.tcscontrol.control_backend.enuns.converters.TypeMaintenanceConverter;
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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MANUTENCOES")
public class Maintenance implements Serializable {

      @Serial
      private static final Long serialVersionUID = 1L;

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      @Column(name = "id_manutencao")
      private Long id;

      @NotNull
      @Column(name = "tp_manutencao", length = 15)
      @Convert(converter = TypeMaintenanceConverter.class)
      private TypeMaintenance tpManutencao;

      @NotNull
      @Column(name = "status_manutencao", length = 15)
      @Convert(converter = MaintenanceStatusConverter.class)
      private MaintenanceStatus maintenanceStatus = MaintenanceStatus.AGENDADA;

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

      @Column(name = "tp_status")
      @Convert(converter = StatusConverter.class)
      private Status tpStatus = Status.ACTIVE;

      @ManyToOne(fetch = FetchType.EAGER, optional = false)
      @JoinColumn(name = "patrimonio_id", nullable = false)
      @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
      private Patrimony patrimony = new Patrimony();

      @ManyToOne(fetch = FetchType.EAGER, optional = false)
      @JoinColumn(name = "fornecedor_id", nullable = false)
      @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
      private Fornecedor fornecedor = new Fornecedor();

      @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Objects.hashCode(getId());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		  if (this == o) {
				return true;
		  }
		  if (o == null || getClass() != o.getClass()) {
				return false;
		  }

		  Maintenance otherMaintenance = (Maintenance) o; 
		  return id != null && id.equals(otherMaintenance.id);
	}

}
