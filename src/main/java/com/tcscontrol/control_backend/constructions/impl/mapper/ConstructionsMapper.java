package com.tcscontrol.control_backend.constructions.impl.mapper;

import com.tcscontrol.control_backend.constructions.model.dto.ConstructionsDTO;
import com.tcscontrol.control_backend.constructions.model.entity.Constructions;

public class ConstructionsMapper {
    
    public ConstructionsDTO toDTO(Constructions constructions) {
        if (constructions == null) {
            return null;
        }

        return new ConstructionsDTO(
                constructions.getId(),
                constructions.getNmObra(),
                constructions.getNmCnpjCpf(),
                constructions.getNmCliente(),
                constructions.getNmCep(),
                constructions.getNmLogradouro(),
                constructions.getNmNumero(),
                constructions.getNmComplemento(),
                constructions.getNmBairro(),
                constructions.getNmCidade(),
                constructions.getNmEstado(),
                constructions.getUser().getId()
                );
    }

    public Constructions toEntity(ConstructionsDTO constructionsDTO){
        if(constructionsDTO == null){
            return null;
        }

        Constructions constructions = new Constructions();
        /*if(constructionsDTO.id() != null){
            constructions.setId(constructionsDTO.id());
        }*/

        constructions.setId(constructionsDTO.id());
        constructions.setNmObra(constructionsDTO.nmObra());
        constructions.setNmCnpjCpf(constructionsDTO.nmCnpjCpf());
        constructions.setNmCliente(constructionsDTO.nmCliente());
        constructions.setNmCep(constructionsDTO.nmCep());
        constructions.setNmLogradouro(constructionsDTO.nmLogradouro());
        constructions.setNmNumero(constructionsDTO.nmNumero());
        constructions.setNmComplemento(constructionsDTO.nmComplemento());
        constructions.setNmBairro(constructionsDTO.nmBairro());
        constructions.setNmCidade(constructionsDTO.nmCidade());
        constructions.setNmEstado(constructionsDTO.nmEstado());
        constructions.getUser().setId((constructionsDTO.id_usuario_responsavel()));

        return constructions;

    }   
}
