package com.plivo.contactBook.dao;

import com.plivo.contactBook.domain.Contact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by m01457 on 03/09/17.
 */

@Transactional
public interface ContactBookDAO extends MongoRepository<Contact, Long>,ContactBookDAOCustom {
    List<Contact> findByOwner(String owner, Pageable pageable);
}
