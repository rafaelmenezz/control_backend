package com.tcscontrol.control_backend.patrymony.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatrimonyRepository extends JpaRepository<Patrimony, Long>{
   

}
