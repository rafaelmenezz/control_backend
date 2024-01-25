package com.tcscontrol.control_backend.pessoa.user.model;

import com.tcscontrol.control_backend.enuns.TypeUser;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByNrCpf(String nrCpf);

    void removeByNrMatricula(String nrMatricula);

    User findByNrMatricula(String nrMatricula);

    @Query("From User u Where u.nrMatricula = ?1")
    UserDetails validarLogin(String matricula);

    List<User> findByTypeUser(TypeUser typeUser);

    @Query("SELECT u " + //
            "FROM User u " + //
            "JOIN Contacts c ON u.id = c.pessoa.id " + //
            "WHERE c.dsContato = :email")
    User obtemUsuarioPorEmail(String email);

    @Query("SELECT u " + //
            "FROM User u " + //
            "JOIN FETCH Contacts c ON u.id = c.pessoa.id " + //
            "WHERE c.typeContacts = 'E-mail' AND u.typeUser = 'Gestor' AND u.tpStatus = 'Ativo'")
    List<User> obtemListaEmailGestores();

    @Query("SELECT u " + //
            "FROM User u " + //
            "JOIN FETCH Contacts c ON u.id = c.pessoa.id " + //
            "WHERE c.typeContacts = 'E-mail' AND u.typeUser = 'Admin' AND u.tpStatus = 'Ativo'")
    List<User> obtemListaEmailAdmin();

}
