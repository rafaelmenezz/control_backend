package com.tcscontrol.control_backend.request_patrimony.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.hibernate.annotations.SQLDelete;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.enuns.converters.StatusConverter;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.requests.model.entity.Requests;

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
@SQLDelete(sql = "UPDATE USUARIO SET fl_status = 'Inativo' Where id = ?")
@Table(name = "REQUISICOES_PATRIMONIO")
public class RequestPatrimony implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dt_previsao_retirada")
    private Date dtPrevisaoRetirada;

    @Column(name = "dt_retirada")
    private Date dtRetirada;

    @Column(name = "dt_previsao_devolucao")
    private Date dtPrevisaoDevolucao;

    @Column(name = "dt_devolucao")
    private Date dtDevolucao;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "requisicao_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Requests requests;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "patrimonio_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Patrimony patrimony;

    @Column(name = "status")
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;

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

        RequestPatrimony otherRequestPatrimony = (RequestPatrimony) o;
        return id != null && id.equals(otherRequestPatrimony.id);
    }

}
