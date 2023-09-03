package com.tcscontrol.control_backend.auth.services;

import java.util.Optional;

import com.tcscontrol.control_backend.pessoa.user.UserNegocio;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserInfoUserDetailsService implements UserDetailsService {

  
    private UserNegocio userNegocio;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userNegocio.login(login));
        return user.map(InfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + login));

    }
}
