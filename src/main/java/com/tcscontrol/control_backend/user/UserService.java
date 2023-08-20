package com.tcscontrol.control_backend.user;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import com.tcscontrol.control_backend.user.model.UserDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface UserService {
    
    public List<UserDTO> list();

    public UserDTO findById(@PathVariable @NotNull @Positive Long id);

    public UserDTO create(@Valid UserDTO user);

    public UserDTO update(Long id, @Valid UserDTO user);

    public void delete(@PathVariable @NotNull @Positive Long id);

}
