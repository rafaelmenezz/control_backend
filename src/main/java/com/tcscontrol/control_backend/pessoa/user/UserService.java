package com.tcscontrol.control_backend.pessoa.user;

import java.util.List;

import com.tcscontrol.control_backend.pessoa.user.model.dto.RecoverPassword;
import com.tcscontrol.control_backend.pessoa.user.model.dto.ReqUpdatePassword;
import com.tcscontrol.control_backend.pessoa.user.model.dto.UserCreateDTO;
import com.tcscontrol.control_backend.pessoa.user.model.dto.UserDTO;
import com.tcscontrol.control_backend.pessoa.user.model.dto.UserSenhaDTO;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public interface UserService {
    
    public List<UserCreateDTO> list();

    public UserCreateDTO findById(@PathVariable @NotNull @Positive Long id);

    public UserCreateDTO create(@Valid UserDTO userDTO, String password);

    public UserCreateDTO update(Long id, @Valid UserCreateDTO user);

    public void delete(@PathVariable @NotNull @Positive Long id);

    public User login(String login);

    public void updatePassword(Long id, ReqUpdatePassword ReqUpdatePassword);

    void deleteCascade(String nrMatricula);

    void register(UserSenhaDTO user);

    UserDetails userLogin(String login);

    User obtemUserMatricula(String matricula);

    void recoverPassword(RecoverPassword recoverPassword);

}
