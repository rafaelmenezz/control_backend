package com.tcscontrol.control_backend.patrimony;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.patrymony.model.Patrimony;
import com.tcscontrol.control_backend.patrymony.model.PatrimonyDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface PatrimonyNegocio extends PatrimonyService {

}
