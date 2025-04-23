package com.app.hubspot.repository;

import com.app.hubspot.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("SELECT c FROM Contact c WHERE c.objectId = :objectId")
    Contact contatoPorObjectId(@Param("objectId") Long objectId);
}
