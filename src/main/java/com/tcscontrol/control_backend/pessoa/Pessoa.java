package com.tcscontrol.control_backend.pessoa;


import com.tcscontrol.control_backend.contacts.model.Contacts;
import com.tcscontrol.control_backend.enuns.DocumentoType;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.enuns.converters.DocumentoConverter;
import com.tcscontrol.control_backend.enuns.converters.StatusConverter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="PESSOA")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="TYPE")
@EqualsAndHashCode(of = {"id"})
public abstract class Pessoa implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nm_name")
    private String nmName;

    @Column(name="tp_documento")
    @Convert(converter = DocumentoConverter.class)
    private DocumentoType typeDocumento;

    @Column(name="tp_status")
    @Convert(converter = StatusConverter.class)
    private Status tpStatus = Status.ACTIVE;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contacts> contacts = new ArrayList<>();
}
