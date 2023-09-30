package com.tcscontrol.control_backend.inventory.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "INVENTARIO")
@AllArgsConstructor
@NoArgsConstructor
public class Inventory implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_inventario")
    private Long id;

    @Column(name = "dt_agendada")
    private Date dtAgendada;

    @Column(name = "dt_realizada")
    private Date dtRealizada;
    
}
