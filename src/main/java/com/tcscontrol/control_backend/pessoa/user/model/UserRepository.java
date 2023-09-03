package com.tcscontrol.control_backend.pessoa.user.model;

import java.util.Optional;

import com.tcscontrol.control_backend.pessoa.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long>{

    User findByNrCpf(String nrCpf);

    void deleteByNrMatricula(Integer nrMatricula);

    Optional<User> findByNrMatricula(Integer nrMatricula);

}
