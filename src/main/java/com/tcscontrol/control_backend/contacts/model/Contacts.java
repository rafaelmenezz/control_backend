package com.tcscontrol.control_backend.contacts.model;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tcscontrol.control_backend.enuns.TypeContacts;
import com.tcscontrol.control_backend.enuns.converters.TypeContactsConverter;
import com.tcscontrol.control_backend.pessoa.Pessoa;

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
import lombok.Data;

@Data
@Entity
@Table(name = "CONTATO")
public class Contacts implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contato")
    private Long id;

    @NotNull
    @Column(name = "tipo_contato", nullable = false)
    @Convert(converter = TypeContactsConverter.class)
    private TypeContacts typeContacts;

    @Column(name = "ds_contato", unique = true, nullable = false, length = 100)
    private String dsContato;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "pessoa_id", nullable = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Pessoa pessoa;

}
