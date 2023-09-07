package com.tcscontrol.control_backend.patrimony.model;

import java.io.Serializable;

import org.hibernate.annotations.Where;

import com.tcscontrol.control_backend.enuns.converters.StatusConverter;

import ch.qos.logback.core.status.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@Where(clause = "ds_ativo = 'Ativo'")
@Table(name = "PATRIMONIO")
public class Patrimony implements Serializable {

private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_patrimonio;

	@Column(name = "nr_serie", nullable = true)
	private Integer nr_serie;
    

	@Column(name = "nr_ativo", nullable = true)
	private Integer nr_ativo;
    

	@Column(length = 15, nullable = false)
	@Convert(converter = StatusConverter.class)
	private Status ds_ativo;

	@Id
	@Column(name = "id_fornecedor",nullable = false,unique = true)
	private Integer id_fornecedor;
    

	@Column(name = "nr_nf")
	private Integer nr_nf;

	@Column(name = "dt_datanf")
	private Data dt_datanf;

	@Column(name = "dt_aquisicao")
	private Data dt_aquisicao;
    
	@Column(name = "vi_aquisicao")
	private Double vi_aquisicao;
    
	@Column(length = 15, nullable = false)
	@Convert(converter = StatusConverter.class)
	private Integer ch_status;
    
	@Column(name = "fl_fixo")
	private boolean fl_fixo;

}
