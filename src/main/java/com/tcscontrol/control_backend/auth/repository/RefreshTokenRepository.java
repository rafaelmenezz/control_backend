package com.tcscontrol.control_backend.auth.repository;

import java.util.Optional;

import com.tcscontrol.control_backend.pessoa.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.auth.model.RefreshToken;



public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long>{
    
    Optional<RefreshToken> findByToken(String token);

    RefreshToken findByUser(User user);

}
