package com.tcscontrol.control_backend.pessoa.user.model.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tcscontrol.control_backend.contacts.model.Contacts;
import com.tcscontrol.control_backend.enuns.DocumentoType;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.enuns.TypeUser;
import com.tcscontrol.control_backend.enuns.converters.TypeUserConverter;
import com.tcscontrol.control_backend.pessoa.Pessoa;
import com.tcscontrol.control_backend.utilitarios.UtilCast;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
// @SQLDelete(sql = "UPDATE USUARIO SET fl_status = 'Inativo' Where id = ?")
@Table(name = "USUARIO")
@EqualsAndHashCode(callSuper = false)
public class User extends Pessoa implements UserDetails {

    @Column(name = "nr_matricula")
    private String nrMatricula;

    @Column(name = "nm_senha")
    private String nmSenha;

    @Column(name = "nr_cpf", unique = true)
    private String nrCpf;

    @Column(name = "ft_foto")
    private String ftFoto;

    @Column(name = "primeiro_acesso")
    private Boolean primeiroAcesso = Boolean.TRUE;

    @NotNull
    @Column(name = "tp_usuario", length = 15)
    @Convert(converter = TypeUserConverter.class)
    private TypeUser typeUser = TypeUser.REQUISITANTE;

    @Builder
    public User(Long id, String nmName, DocumentoType documentoType, Status status,
            List<Contacts> contacts, String nrMatricula, String nmSenha, String nrCPF, String ftFoto,
            TypeUser typeUser) {
        super(id, nmName, documentoType, status, contacts);
        this.nrMatricula = nrMatricula;
        this.nmSenha = nmSenha;
        this.nrCpf = nrCPF;
        this.ftFoto = ftFoto;
        this.typeUser = typeUser;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.typeUser == TypeUser.ADMIN || this.typeUser == TypeUser.GESTOR)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.nmSenha;
    }

    @Override
    public String getUsername() {
        return UtilCast.toString(this.nrMatricula);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
