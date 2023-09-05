package com.tcscontrol.control_backend.obras;

import java.io.Serializable;
import java.util.UUID;

import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "OBRAS")
public class obras implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private User user;
}
