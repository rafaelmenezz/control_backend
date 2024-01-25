package com.tcscontrol.control_backend.pessoa.fornecedor;

import java.util.ArrayList;
import java.util.List;

import com.tcscontrol.control_backend.contacts.model.Contacts;
import com.tcscontrol.control_backend.enuns.DocumentoType;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.maintenance.model.entity.Maintenance;
import com.tcscontrol.control_backend.patrimony.model.entity.Patrimony;
import com.tcscontrol.control_backend.pessoa.Pessoa;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FORNECEDOR")
@EqualsAndHashCode(callSuper = false)
public class Fornecedor extends Pessoa {

    private String nrCnpj;

    @OneToMany
    private List<Patrimony> patrimonies = new ArrayList<>();

    @OneToMany(orphanRemoval = false, mappedBy = "fornecedor")
    private List<Maintenance> maintenances = new ArrayList<>(); 

    @Builder
    public Fornecedor(Long id, String nmName,List<Contacts> contacts, String nrCnpj ){
        super(id, nmName, DocumentoType.CNPJ, Status.ACTIVE, contacts);
        this.nrCnpj = nrCnpj;
    }
    
    
}
