package com.tcscontrol.control_backend.patrimony.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.enuns.converters.StatusConverter;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PATRIMONIOS")
public class Patrimony implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "nr_serie", nullable = true)
	private Integer nrSerie;

    @Column(name = "nm_patrimonio")
    private String nmPatrimonio;
    
	@Column(name = "ds_patrimonio")
	private String dsPatrimonio;
    
	@Column(name = "nr_nota_fiscal")
	private Integer nrNotaFiscal;

	@Column(name = "dt_nota_fiscal")
	private Date dtNotaFiscal;

	@Column(name = "dt_aquisicao")
	private Date dtAquisicao;
    
	@Column(name = "vl_aquisicao")
	private Double vlAquisicao;
        
	@Column(name = "fl_fixo")
	private Boolean flFixo;

    @Column(name="tp_status")
    @Convert(converter = StatusConverter.class)
    private Status tpStatus = Status.ACTIVE;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "id_fornecedor", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Fornecedor fornecedor;
}
