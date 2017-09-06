package com.plivo.contactBook.service;

import com.plivo.contactBook.dao.ContactBookDAO;
import com.plivo.contactBook.domain.Contact;
import com.plivo.contactBook.response.ContactResponse;
import com.plivo.contactBook.response.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by m01457 on 03/09/17.
 */

@RestController
public class ContactBookService {

    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(ContactBookService.class.getName());

    @Autowired
    private ContactBookDAO contactBookDAO;

    @PostMapping(value = "{owner}/contact", produces = "application/json", consumes = "application/json")
    public ContactResponse addContact(@PathVariable String owner, @RequestBody Contact contact) {

        ContactResponse contactResponse = new ContactResponse();
        List<Contact> responseData = new ArrayList<Contact>();

        //validate the contact
        if (owner == null || owner.isEmpty() || contact == null || contact.getContactEmail() == null || contact.getContactEmail().isEmpty() ||
                contact.getContactName() == null || contact.getContactName().isEmpty()) {
            logger.severe("invalid contact cannot be created");

            contactResponse.setStatus(new StatusResponse(StatusResponse.Type.ERROR, "invalid contact cannot be created"));


        } else {
            //set the owner for the contact
            contact.setOwner(owner);
            //create the contact
            try {
                Contact createdContact = contactBookDAO.save(contact);
                responseData.add(createdContact);
                contactResponse.setStatus(new StatusResponse(StatusResponse.Type.SUCCESS));
                contactResponse.setData(responseData);

            } catch (Exception ex) {
                logger.severe("contact creation failed:" + ex.getMessage());
                contactResponse.setStatus(new StatusResponse(StatusResponse.Type.ERROR, "Contact Creation failed because of an error or contact already exists"));

            }

        }

        return contactResponse;

    }


    @RequestMapping("/contacts")
    public ContactResponse getAllContacts(@PageableDefault Pageable pageable) {

        ContactResponse contactResponse = new ContactResponse();

        try {
            Page<Contact> contacts = contactBookDAO.findAll(pageable);
            //logger.info("contacts size is:"+contacts.getSize());
            contactResponse.setStatus(new StatusResponse(StatusResponse.Type.SUCCESS));
            contactResponse.setData(contacts.getContent());

        }catch(Exception ex)
        {
            logger.severe("error getting contacts:"+ex.getMessage());
            contactResponse.setStatus(new StatusResponse(StatusResponse.Type.ERROR, ex.getMessage()));

        }

        return contactResponse;

    }


    @RequestMapping("{owner}/contacts")
    public ContactResponse getContactsByOwner(@PathVariable String owner, @PageableDefault Pageable pageable) {
        ContactResponse contactResponse = new ContactResponse();

        if(owner == null || owner.isEmpty())
        {
            contactResponse.setStatus(new StatusResponse((StatusResponse.Type.ERROR)));
        }
        else
        {
            try {
                List<Contact> contacts = contactBookDAO.findByOwner(owner, pageable);
                contactResponse.setStatus(new StatusResponse(StatusResponse.Type.SUCCESS));
                contactResponse.setData(contacts);
            }catch (Exception ex)
            {
                logger.severe("error getting contacts:"+ex.getMessage());
                contactResponse.setStatus(new StatusResponse(StatusResponse.Type.ERROR, ex.getMessage()));
            }

        }
        return contactResponse;
    }


    @RequestMapping("{owner}/contact/{id}")
    public ContactResponse getContactById(@PathVariable String owner, @PathVariable String id) {

        ContactResponse contactResponse = new ContactResponse();
        List<Contact> contacts = new ArrayList<Contact>();

        if(owner == null || owner.isEmpty() || id == null || id.isEmpty())
        {
            contactResponse.setStatus(new StatusResponse((StatusResponse.Type.ERROR)));
        }
        else
        {
            try {
                Contact contact = contactBookDAO.findById(id);
                contacts.add(contact);
                contactResponse.setStatus(new StatusResponse(StatusResponse.Type.SUCCESS));
                contactResponse.setData(contacts);
            }catch (Exception ex)
            {
                logger.severe("error getting contacts:"+ex.getMessage());
                contactResponse.setStatus(new StatusResponse(StatusResponse.Type.ERROR, ex.getMessage()));
            }

        }
        return contactResponse;


    }

    @RequestMapping("{owner}/contacts/search")
    public ContactResponse searchContacts(@PathVariable String owner, @QueryParam("query") String query, @PageableDefault Pageable pageable) {

        ContactResponse contactResponse = new ContactResponse();
        if (owner == null || owner.isEmpty() || query == null || query.isEmpty()) {
            contactResponse.setStatus(new StatusResponse((StatusResponse.Type.ERROR)));
        } else {
            try {
                List<Contact> contacts = contactBookDAO.searchContacts(query, owner, pageable);
                contactResponse.setStatus(new StatusResponse(StatusResponse.Type.SUCCESS));
                contactResponse.setData(contacts);
            } catch (Exception ex) {
                logger.severe("error searching contacts:" + ex.getMessage());
                contactResponse.setStatus(new StatusResponse(StatusResponse.Type.ERROR, ex.getMessage()));
            }

        }
        return contactResponse;
    }

    @DeleteMapping("{owner}/contact")
    public ContactResponse deleteContact(@PathVariable String owner, @QueryParam("query") String query) {

        ContactResponse contactResponse = new ContactResponse();

        if (owner == null || owner.isEmpty() || query == null || query.isEmpty()) {
            contactResponse.setStatus(new StatusResponse((StatusResponse.Type.ERROR)));
        } else {

            try {
                Pageable pageable = new PageRequest(0, 1);

                List<Contact> contacts = contactBookDAO.searchContacts(query, owner, pageable);

                if (contacts != null && contacts.size() == 1) {
                    contactBookDAO.delete(contacts.get(0));
                    contactResponse.setStatus(new StatusResponse(StatusResponse.Type.SUCCESS));

                } else {
                    contactResponse.setStatus(new StatusResponse(StatusResponse.Type.ERROR, "contact not found"));
                }

            } catch (Exception ex) {
                logger.severe("error deleting contacts:" + ex.getMessage());
                contactResponse.setStatus(new StatusResponse(StatusResponse.Type.ERROR, ex.getMessage()));

            }

        }


        return contactResponse;


    }


    @PutMapping("{owner}/contact/{id}")
    public ContactResponse updateContact(@PathVariable String owner, @PathVariable String id, @RequestBody Contact contact) {

        ContactResponse contactResponse = new ContactResponse();
        List<Contact> contacts = new ArrayList<Contact>();

        if (owner == null || owner.isEmpty() || id == null || id.isEmpty() || contact == null) {
            contactResponse.setStatus(new StatusResponse((StatusResponse.Type.ERROR)));
        } else {

            try {
                Contact fetchedContact = contactBookDAO.findById(id);
                if (fetchedContact != null) {
                    contact.setId(fetchedContact.getId());
                    fetchedContact = contactBookDAO.save(contact);
                    contacts.add(fetchedContact);
                    contactResponse.setStatus(new StatusResponse(StatusResponse.Type.SUCCESS));
                    contactResponse.setData(contacts);

                } else {
                    contactResponse.setStatus(new StatusResponse(StatusResponse.Type.ERROR, "contact not found"));
                }
            } catch (Exception ex) {
                logger.severe("error updating contacts:" + ex.getMessage());
                contactResponse.setStatus(new StatusResponse(StatusResponse.Type.ERROR, ex.getMessage()));

            }


        }


        return contactResponse;


    }

}
