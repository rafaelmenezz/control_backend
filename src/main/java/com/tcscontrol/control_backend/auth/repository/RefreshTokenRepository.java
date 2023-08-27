package com.tcscontrol.control_backend.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.auth.model.RefreshToken;
import com.tcscontrol.control_backend.user.model.entity.User;



public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long>{
    
    Optional<RefreshToken> findByToken(String token);

    
    RefreshToken findByUser(User user);

}
