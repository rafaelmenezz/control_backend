package com.tcscontrol.control_backend.department.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcscontrol.control_backend.department.DepartmentNegocio;
import com.tcscontrol.control_backend.department.impl.mapper.DepartmentMapper;
import com.tcscontrol.control_backend.department.model.dto.DepartmentCreateDTO;
import com.tcscontrol.control_backend.department.model.dto.DepartmentDTO;
import com.tcscontrol.control_backend.department.repository.DepartmentRepository;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.pessoa.user.impl.mapper.UserMapper;
import com.tcscontrol.control_backend.pessoa.user.model.UserRepository;
import com.tcscontrol.control_backend.pessoa.user.model.dto.UserCreateDTO;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@Component(value = "departmentNegocio")
@AllArgsConstructor
public class DepartmentNegocioImpl implements DepartmentNegocio{
    
    private DepartmentRepository departmentRepository;
    private UserRepository userRepository;
    private DepartmentMapper departmentMapper;
    private UserMapper userMapper;

    @Override
    public List<DepartmentDTO> list() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO findById(@NotNull Long id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Override
    public DepartmentDTO create(@Valid @NotNull DepartmentCreateDTO departmentCreateDTO) {
        return userRepository.findById(departmentCreateDTO.idUsuario()).map(recordFound -> {
            UserCreateDTO u = userMapper.toCreateDto(recordFound);
            DepartmentDTO departmentDTO = new DepartmentDTO(
                null,
                departmentCreateDTO.nmDepartamento(),
                u
            );
            return departmentMapper.toDTO(departmentRepository.save(departmentMapper.toEntity(departmentDTO)));
        }).orElseThrow(()-> new RecordNotFoundException("Usuário não encontrado"));
    }

    @Override
    public DepartmentDTO update(@Valid @NotNull DepartmentCreateDTO department) {
        return departmentRepository.findById(department.id())
                .map(recordFound -> {
                    User u = userRepository.findById(department.idUsuario()).get();
                    recordFound.setNmDepartamento(department.nmDepartamento());
                    recordFound.setUser(u);
                    return departmentMapper.toDTO(departmentRepository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(department.id()));        
    }

    @Override
    public void delete(@NotNull @Positive Long id) {
        departmentRepository
                .delete(departmentRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
    }

}
