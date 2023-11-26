package com.tcscontrol.control_backend.patrimony;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcscontrol.control_backend.patrimony.model.entity.LossTheft;

public interface LossTheftRepository extends JpaRepository<LossTheft ,Long> {

    @Query("SELECT lt " + 
       "FROM LossTheft lt " +
        "WHERE lt.dtLost BETWEEN :dataInicial AND :dataFinal")
    public List<LossTheft> obterRelatorioBaixa (Date dataInicial, Date dataFinal);

       @Query("SELECT lt " + 
       "FROM LossTheft lt")
    public List<LossTheft> obterRelatorioBaixa();
    
}
