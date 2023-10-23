package com.tcscontrol.control_backend.patrimony.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tcscontrol.control_backend.allocation_patrimony.model.entity.AllocationPatrimony;
import com.tcscontrol.control_backend.enuns.SituationType;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.enuns.converters.StatusConverter;
import com.tcscontrol.control_backend.enuns.converters.TypeSituationConverter;
import com.tcscontrol.control_backend.pessoa.fornecedor.Fornecedor;
import com.tcscontrol.control_backend.request_patrimony.model.entity.RequestPatrimony;
import com.tcscontrol.control_backend.warranty.model.entity.Warranty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PATRIMONIOS")
public class Patrimony implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_patrimonio")
	private Long id;

	@Column(name = "nr_serie", nullable = true)
	private String nrSerie;

	@Column(name = "nm_patrimonio")
	private String nmPatrimonio;

	@Lob
	@Column(name = "ds_patrimonio", length = 3000)
	private String nmDescricao;

	@Column(name = "nr_nota_fiscal")
	private Integer nrNotaFiscal;

	@Column(name = "dt_nota_fiscal")
	private Date dtNotaFiscal;

	@Column(name = "dt_aquisicao")
	private Date dtAquisicao;

	@Column(name = "vl_aquisicao")
	private Double vlAquisicao;

	@Column(name = "fl_fixo")
	private Boolean fixo;

	@Column(name = "tp_situacao")
	@Convert(converter = TypeSituationConverter.class)
	private SituationType tpSituacao = SituationType.DISPONIVEL;

	@Column(name = "tp_status")
	@Convert(converter = StatusConverter.class)
	private Status tpStatus = Status.ACTIVE;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "patrimony")
	private List<Warranty> warrantys = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = false, mappedBy = "patrimony")
	private List<RequestPatrimony> requests = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "patrimony")
	private List<AllocationPatrimony> allocations = new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "fornecedor_id", nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Fornecedor fornecedor;

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

		  Patrimony otherPatrimony = (Patrimony) o; 
		  return id != null && id.equals(otherPatrimony.id);
	}
}
