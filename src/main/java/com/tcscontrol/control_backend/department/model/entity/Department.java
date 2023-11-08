package com.tcscontrol.control_backend.department.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.tcscontrol.control_backend.allocation.model.Allocation;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.enuns.converters.StatusConverter;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "DEPARTAMENTOS")
@SQLDelete(sql = "UPDATE departamentos SET status = 'Inativo' WHERE id_departamento = ?")
@Where(clause = "status = 'Ativo'")
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_departamento")
    private Long id;

    @Column(name = "nm_departamento")
    private String nmDepartamento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = true)
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Allocation> allocations = new ArrayList<>();

    @Column(name="status")
    @Convert(converter = StatusConverter.class)
    private Status tpStatus = Status.ACTIVE;

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Objects.hashCode(getId());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		  if (this == o) {
				return true;
		  }
		  if (o == null || getClass() != o.getClass()) {
				return false;
		  }

		  Department otherDepartment = (Department) o; 
		  return id != null && id.equals(otherDepartment.id);
	}

}
