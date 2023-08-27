package com.tcscontrol.control_backend.user.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcscontrol.control_backend.user.model.entity.User;


public interface UserRepository extends JpaRepository<User, Long>{

    @Query("SELECT u FROM User u WHERE u.nrMatricula = ?1")
    Optional<User> login(Integer login);

    void deleteByNrMatricula(Integer nrMatricula);

    Optional<User> findByNrMatricula(Integer nrMatricula);

    Optional<User> findByNrCpf(String nrCpf);

}
