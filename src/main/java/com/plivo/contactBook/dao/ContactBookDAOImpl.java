package com.plivo.contactBook.dao;

import com.plivo.contactBook.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by m01457 on 03/09/17.
 */


public class ContactBookDAOImpl implements  ContactBookDAOCustom{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Contact> searchContacts(String query, String owner, Pageable pageable) {


        return mongoTemplate.find(Query.query(new Criteria()
                .andOperator(Criteria.where("owner").regex(owner))
                .orOperator(Criteria.where("contactName").regex(query, "i"),
                        Criteria.where("contactEmail").regex(query, "i")
                        )
        ).with(pageable), Contact.class);




    }

    @Override
    public Contact findById(String id) {

        return mongoTemplate.findById(id, Contact.class);

    }




}
