package com.tcscontrol.control_backend.pessoa.user.model;


import com.tcscontrol.control_backend.pessoa.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByNrCpf(String nrCpf);

    void removeByNrMatricula(Integer nrMatricula);

    User findByNrMatricula(Integer nrMatricula);

    @Query("From User u Where u.nrMatricula = ?1")
    UserDetails validarLogin(Integer matricula);

}
