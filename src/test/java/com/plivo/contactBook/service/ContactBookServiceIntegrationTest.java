package com.plivo.contactBook.service;


import com.plivo.contactBook.dao.ContactBookDAO;
import com.plivo.contactBook.domain.Contact;
import com.plivo.contactBook.response.ContactResponse;
import com.plivo.contactBook.response.StatusResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by m01457 on 05/09/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ContactBookServiceIntegrationTest {

    @Autowired
    private ContactBookDAO contactBookDAO;

    @Autowired
    private  ContactBookService contactBookService;

    @Test
    public void addContact() throws Exception {

        //Create a new contact using the contactBookDAO API
        Contact contact = new Contact("chakri", "divya", "divya@gmail.com");
        try {

            List<Contact> contactList = contactBookService.addContact("chakri", contact).getData();

            assertNotNull(contactList);

            assertEquals(contactList.size(), 1);

            Contact createdContact = contactList.get(0);

            String contactId = createdContact.getId();

            assertEquals(createdContact.getContactName(), contact.getContactName());

            List<Contact> contactList1 = contactBookService.getContactById("chakri", contactId).getData();

            assertNotNull(contactList1);

            assertEquals(contactList1.size(), 1);

            Contact getContact = contactList1.get(0);

            assertEquals(getContact.getId(), contactId);
            assertEquals(getContact.getOwner(), createdContact.getOwner());
            assertEquals(getContact.getContactName(), createdContact.getContactName());
            assertEquals(getContact.getContactEmail(), createdContact.getContactEmail());

        } finally {
            //Delete the Test data created
            contactBookDAO.delete(contact);
        }
    }

    @Test
    public void getAllContacts() throws Exception {

        //Create a new contact using the contactBookDAO API
        Contact contact = new Contact("chakri", "divya", "divya@gmail.com");
        try {
            contactBookDAO.save(contact);

            String contactId = contact.getId();

            Pageable pageable = new PageRequest(0, 1);

            List<Contact> contactList = contactBookService.getAllContacts(pageable).getData();

            assertNotNull(contactList);

            assertEquals(contactList.size(), 1);

            Contact apiResponse = contactList.get(0);


            //Verify that the data from the API and data saved in the DB are same
            assertNotNull(apiResponse);

            assertEquals(contact.getContactName(), apiResponse.getContactName());
            assertEquals(contact.getContactEmail(), apiResponse.getContactEmail());
            assertEquals(contact.getOwner(), apiResponse.getOwner());
        }finally {
            //Delete the Test data created
            contactBookDAO.delete(contact);
        }





    }

    @Test
    public void getContactsByOwner()  throws  Exception {

        Contact contact = new Contact("chakri", "divya", "divya@gmail.com");

        try {
            //Create a new contact using the contactBookDAO API

            contactBookDAO.save(contact);

            String contactId = contact.getId();

            Pageable pageable = new PageRequest(0, 1);

            List<Contact> contactList = contactBookService.getContactsByOwner("chakri", pageable).getData();

            assertNotNull(contactList);

            assertEquals(contactList.size(), 1);

            Contact apiResponse = contactList.get(0);


            //Verify that the data from the API and data saved in the DB are same
            assertNotNull(apiResponse);

            assertEquals(contact.getContactName(), apiResponse.getContactName());
            assertEquals(contact.getContactEmail(), apiResponse.getContactEmail());
            assertEquals(contact.getOwner(), apiResponse.getOwner());



            ContactResponse contactResponse = contactBookService.getContactsByOwner("", pageable);

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);

            contactResponse = contactBookService.getContactsByOwner(null, pageable);

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);


        }finally {
            //Delete the Test data created
            contactBookDAO.delete(contact);
        }
    }


    @Test
    public void getContactById() throws Exception {

        Contact contact = new Contact("chakri", "divya", "divya@gmail.com");

        try {

            //Create a new contact using the contactBookDAO API

            contactBookDAO.save(contact);

            String contactId = contact.getId();

            Pageable pageable = new PageRequest(0, 1);

            List<Contact> contactList = contactBookService.getContactById("chakri", contactId).getData();

            assertNotNull(contactList);

            assertEquals(contactList.size(), 1);

            Contact apiResponse = contactList.get(0);


            //Verify that the data from the API and data saved in the DB are same
            assertNotNull(apiResponse);

            assertEquals(contact.getContactName(), apiResponse.getContactName());

            assertEquals(contact.getContactEmail(), apiResponse.getContactEmail());

            assertEquals(contact.getOwner(), apiResponse.getOwner());



            ContactResponse contactResponse = contactBookService.getContactById("", contactId);

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);

            contactResponse = contactBookService.getContactById(null, contactId);

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);

            contactResponse = contactBookService.getContactById("chakri", null);

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);





        } finally {
            //Delete the Test data created
            contactBookDAO.delete(contact);
        }
    }

    @Test
    public void searchContacts() throws Exception{


        Contact contact = new Contact("chakri", "divya", "divya@gmail.com");

        try {

            //Create a new contact using the contactBookDAO API

            contactBookDAO.save(contact);

            String contactId = contact.getId();

            Pageable pageable = new PageRequest(0, 1);

            List<Contact> contactList = contactBookService.searchContacts("chakri", "divya", pageable).getData();

            assertNotNull(contactList);

            assertEquals(contactList.size(), 1);

            Contact apiResponse = contactList.get(0);


            //Verify that the data from the API and data saved in the DB are same
            assertNotNull(apiResponse);

            assertEquals(contact.getContactName(), apiResponse.getContactName());

            assertEquals(contact.getContactEmail(), apiResponse.getContactEmail());

            assertEquals(contact.getOwner(), apiResponse.getOwner());



            ContactResponse contactResponse = contactBookService.searchContacts("", "divya", pageable);

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);

            contactResponse = contactBookService.searchContacts(null, "divya", pageable);

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);

            contactResponse = contactBookService.searchContacts("chakri", "", pageable);

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);

            contactResponse = contactBookService.searchContacts("chakri", null, pageable);

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);





        } finally {
            //Delete the Test data created
            contactBookDAO.delete(contact);
        }

    }



    @Test
    public void deleteContact() throws Exception {


        Contact contact = new Contact("chakri", "divya", "divya@gmail.com");

        try {

            //Create a new contact using the contactBookDAO API

            contactBookDAO.save(contact);

            String contactId = contact.getId();

            Pageable pageable = new PageRequest(0, 1);

            ContactResponse contactResponse = contactBookService.deleteContact("chakri", "divya");

            assertEquals(contactResponse.getStatus().getStatusType().SUCCESS, StatusResponse.Type.SUCCESS);

            contactResponse = contactBookService.searchContacts("chakri", "divya", pageable);

            assertEquals(contactResponse.getData().size(), 0);

            contactResponse = contactBookService.deleteContact(null, "divya");

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);

            contactResponse = contactBookService.deleteContact("", "divya");

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);

            contactResponse = contactBookService.deleteContact("chakri", null);

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);

            contactResponse = contactBookService.deleteContact("chakri", "");

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);



        } finally {
            //Delete the Test data created
            contactBookDAO.delete(contact);
        }


    }

    @Test
    public void updateContact() throws Exception {


        Contact contact = new Contact("chakri", "divya", "divya@gmail.com");

        try {

            //Create a new contact using the contactBookDAO API

            Contact createdContact = contactBookDAO.save(contact);

            String contactId = createdContact.getId();

            Pageable pageable = new PageRequest(0, 1);

            //change the contact details
            createdContact.setContactName("dhanya");

            List<Contact> contactList  = contactBookService.updateContact("chakri", contactId, createdContact).getData();


            assertNotNull(contactList);

            assertEquals(contactList.size(), 1);

            Contact updatedContact = contactList.get(0);


            //Verify that the data from the API and data saved in the DB are same
            assertNotNull(updatedContact);

            assertEquals(updatedContact.getContactName(), createdContact.getContactName());



            ContactResponse contactResponse = contactBookService.updateContact(null, contactId, createdContact);

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);


            contactResponse = contactBookService.updateContact("", contactId, createdContact);

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);

            contactResponse = contactBookService.updateContact("chakri", "", createdContact);

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);

            contactResponse = contactBookService.updateContact("chakri", null, createdContact);

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);


            contactResponse = contactBookService.updateContact("chakri", contactId, null);

            assertEquals(contactResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);


        } finally {
            //Delete the Test data created
            contactBookDAO.delete(contact);
        }
    }


}
