package com.tcscontrol.control_backend.pessoa.user.model;


import com.tcscontrol.control_backend.pessoa.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    User findByNrCpf(String nrCpf);

    void removeByNrMatricula(Integer nrMatricula);

    User findByNrMatricula(Integer nrMatricula);

}
