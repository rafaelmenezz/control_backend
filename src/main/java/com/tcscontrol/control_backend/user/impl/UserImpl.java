package com.tcscontrol.control_backend.user.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.user.UserNegocio;
import com.tcscontrol.control_backend.user.impl.mapper.UserMapper;
import com.tcscontrol.control_backend.user.model.User;
import com.tcscontrol.control_backend.user.model.UserDTO;
import com.tcscontrol.control_backend.user.model.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Component(value = "userNegocio")
public class UserImpl implements UserNegocio {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserImpl(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTO> list(){
        return userRepository.findAll()
        .stream()
        .map(userMapper::toDto)
        .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(@NotNull @Positive Long id) {
        return userRepository.findById(id)
        .map(userMapper::toDto)
        .orElseThrow(()-> new RecordNotFoundException(id)); 
    }

    @Override
    public UserDTO create(@Valid @NotNull UserDTO userDto) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

    @Override
    public UserDTO update(Long id, @Valid @NotNull UserDTO userDto) {
        return userRepository.findById(id)
        .map(recordFound -> {
            User user = userMapper.toEntity(userDto);
            recordFound.setNmUsuario(userDto.nmUsuario());
            recordFound.setFtFoto(userDto.ftFoto());
            recordFound.setTypeUser(userMapper.convertTypeUserValue(userDto.typeUser()));
            recordFound.setFlStatus(userMapper.convertStatusValue(userDto.flStatus()));
            recordFound.getContacts().clear();
            user.getContacts().forEach(recordFound.getContacts()::add);
            return userMapper.toDto(userRepository.save(recordFound));
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Override
    public void delete(@NotNull @Positive Long id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
    }


    
}
