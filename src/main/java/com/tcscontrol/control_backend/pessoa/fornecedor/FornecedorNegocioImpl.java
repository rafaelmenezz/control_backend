package com.tcscontrol.control_backend.pessoa.fornecedor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@Component(value = "fornecedorNegocio")
@AllArgsConstructor
public class FornecedorNegocioImpl implements FornecedorNegocio {

    FornecedorRepository fornecedorRepository;
    FornecedorMapper fornecedorMapper;

    @Override
    public List<FornecedorDTO> list() {
        return fornecedorRepository.findAll()
                .stream()
                .map(fornecedorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FornecedorDTO findById(Long id) {
        return fornecedorRepository.findById(id)
                .map(fornecedorMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Override
    public FornecedorDTO create(@Valid @NotNull FornecedorDTO fornecedorDTO) {
        return fornecedorMapper.toDTO(fornecedorRepository.save(fornecedorMapper.toEntity(fornecedorDTO)));
    }

    @Override
    public FornecedorDTO update(Long id, @Valid @NotNull FornecedorDTO fornecedorDTO) {
        return fornecedorRepository.findById(id)
                .map(recordFound -> {
                    Fornecedor fornecedor = fornecedorMapper.toEntity(fornecedorDTO);
                    recordFound.setNmName(fornecedorDTO.nmNome());
                    recordFound.setNrCnpj(fornecedorDTO.nrCnpj());
                    recordFound.setTpStatus(fornecedorMapper.convertStatusValue(fornecedorDTO.flStatus()));
                    recordFound.getContacts().clear();
                    fornecedor.getContacts().forEach(recordFound.getContacts()::add);
                    return fornecedorMapper.toDTO(fornecedorRepository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Override
    public void delete(@NotNull @Positive Long id) {
        fornecedorRepository
                .delete(fornecedorRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
    }

    @Override
    public Fornecedor pesquisaFornecedorCnpj(String nrCnpj) {
        if (nrCnpj == null) {
            return null;
        }
        return fornecedorRepository.findByNrCnpj(nrCnpj);
    }

    @Override
    public Fornecedor cadastrarFornecedor(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    @Override
    public Fornecedor obtemFornecedor(String cnpj) {
        Fornecedor f = pesquisaFornecedorCnpj(cnpj);
        if (UtilObjeto.isEmpty(f)) {
            f = new Fornecedor();
            f.setNrCnpj(cnpj);
        }
        return f;
    }
}
