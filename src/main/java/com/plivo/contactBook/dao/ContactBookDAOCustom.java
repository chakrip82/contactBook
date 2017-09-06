package com.plivo.contactBook.dao;

import com.plivo.contactBook.domain.Contact;
import com.plivo.contactBook.exception.ContactNotFoundException;
import com.plivo.contactBook.exception.InvalidContactException;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by m01457 on 03/09/17.
 */
public interface ContactBookDAOCustom {

    public List<Contact> searchContacts(String text, String owner, Pageable pageable) throws ContactNotFoundException;
    public Contact findById(String id) throws InvalidContactException;
}

