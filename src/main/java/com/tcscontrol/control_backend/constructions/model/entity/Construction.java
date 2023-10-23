package com.tcscontrol.control_backend.constructions.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;
import com.tcscontrol.control_backend.requests.model.entity.Requests;

import jakarta.persistence.CascadeType;
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
@Table(name = "OBRAS")
@AllArgsConstructor
@NoArgsConstructor
public class Construction implements Serializable {

      @Serial
      private static final long serialVersionUID = 1L;

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      @Column(name = "id_obra")
      private Long id;

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

      @Column(name = "nm_bairro")
      private String nmBairro;

      @Column(name = "nr_numero")
      private Integer nrNumero;

      @Column(name = "nm_complemento")
      private String nmComplemento;

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
      @JoinColumn(name = "usuario_id", nullable = false)
      @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
      private User user;

      @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "construction")
      private List<Requests> requests;

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

		Construction otherConstruction = (Construction) o;
		return id != null && id.equals(otherConstruction.id);
	}

}
