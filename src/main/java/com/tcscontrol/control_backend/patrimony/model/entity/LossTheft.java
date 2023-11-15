package com.tcscontrol.control_backend.patrimony.model.entity;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BAIXAS_PATRIMONIO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LossTheft {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_baixa_patrimonio")
    private Long id;

    @OneToOne
    @JoinColumn(name="patrimonio_id")
    private Patrimony patrimony;

    @Column(name="dt_perda_roubo")
    private Date dtLost;

    @Column(name = "nm_observacao")
    private String nmObservation;

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

        LossTheft otherCasualyty = (LossTheft) o;
        return id != null && id.equals(otherCasualyty.id);
    }
}
