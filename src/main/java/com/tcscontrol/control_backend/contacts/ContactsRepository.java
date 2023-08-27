package com.tcscontrol.control_backend.contacts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcscontrol.control_backend.contacts.model.Contacts;


public interface ContactsRepository extends JpaRepository<Contacts, Long>{

    Contacts findByDsContato(String dsContato);
    
}
