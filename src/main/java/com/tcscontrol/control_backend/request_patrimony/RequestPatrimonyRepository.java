package com.tcscontrol.control_backend.request_patrimony;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcscontrol.control_backend.request_patrimony.model.entity.RequestPatrimony;

public interface RequestPatrimonyRepository extends JpaRepository<RequestPatrimony, Long>{
    
       @Query("SELECT rp " + //
            "FROM AllocationPatrimony rp " + //
            "JOIN FETCH rp.patrimony p " + //
            "WHERE rp.patrimony.id IN (:ids) AND rp.status = 'Ativo'")
    List<RequestPatrimony> getList(List<Long> ids);
}
