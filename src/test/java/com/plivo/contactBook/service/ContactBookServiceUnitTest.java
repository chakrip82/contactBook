package com.plivo.contactBook.service;

import com.plivo.contactBook.dao.ContactBookDAO;
import com.plivo.contactBook.domain.Contact;
import com.plivo.contactBook.exception.ContactNotFoundException;
import com.plivo.contactBook.exception.InvalidContactException;
import com.plivo.contactBook.response.ContactResponse;
import com.plivo.contactBook.response.StatusResponse;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

/**
 * Created by m01457 on 04/09/17.
 */

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ContactBookServiceUnitTest extends TestCase{

    @Mock
    private ContactBookDAO contactBookDAO;

    @InjectMocks
    private ContactBookService contactBookService;



    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
    }



    @Test
    public void addContact() throws Exception {


        Contact contact = new Contact("chakri", "divya", "divya@gmail.com");

        when(contactBookDAO.save(contact)).thenReturn(contact);

        List<Contact> result = contactBookService.addContact("chakri", contact).getData();

        System.out.println("result size is :" + result.size());

        assertEquals(1, result.size());

        System.out.println("ContactName is :" + result.get(0).getContactName());

        assertEquals("divya", result.get(0).getContactName());


        StatusResponse statusResponse = contactBookService.addContact("", contact).getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);


        statusResponse = contactBookService.addContact(null, contact).getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);


        contact = new Contact("chakri", "", "divya@gmail.com");

        statusResponse = contactBookService.addContact("chakri", contact).getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);


        contact = new Contact("chakri", "divya", null);

        statusResponse = contactBookService.addContact("chakri", contact).getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);


        statusResponse = contactBookService.addContact("chakri", null).getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);


    }

    @Test
    public void getAllContacts() throws Exception {

        List<Contact> contactList = new ArrayList<Contact>();

        contactList.add(new Contact("chakri","divya","divya@gmail.com"));
        contactList.add(new Contact("divya","dhanya","dhanya@gmail.com"));
        contactList.add(new Contact("dhanya","chakri","chakri@gmail.com"));

        Pageable pageable = new PageRequest(0,3);

        Page<Contact> expectedPage = new PageImpl(contactList);

        when(contactBookDAO.findAll(pageable)).thenReturn(expectedPage);

        List<Contact> result = contactBookService.getAllContacts(pageable).getData();

        System.out.println("result size is :"+result.size());

        assertEquals(3, result.size());

    }

    @Test
    public void getContactsByOwner()  throws  Exception {
        List<Contact> contactList = new ArrayList<Contact>();
        contactList.add(new Contact("chakri", "divya", "divya@gmail.com"));

        Pageable pageable = new PageRequest(0, 3);

        when(contactBookDAO.findByOwner("chakri", pageable)).thenReturn(contactList);

        List<Contact> result = contactBookService.getContactsByOwner("chakri", pageable).getData();

        System.out.println("result size is :" + result.size());

        assertEquals(1, result.size());

        assertEquals("divya@gmail.com", result.get(0).getContactEmail());


        //when(contactBookDAO.findByOwner(null, pageable)).

        StatusResponse statusResponse = contactBookService.getContactsByOwner("", pageable).getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);

        statusResponse = contactBookService.getContactsByOwner(null, pageable).getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);




    }

    @Test
    public void getContactById() throws Exception {

        Contact contact = new Contact("chakri", "divya", "divya@gmail.com");

        Pageable pageable = new PageRequest(0, 3);

        when(contactBookDAO.findById("1")).thenReturn(contact);

        List<Contact> result = contactBookService.getContactById("chakri", "1").getData();

        System.out.println("result size is :" + result.size());

        assertEquals(1, result.size());

        assertEquals("divya@gmail.com", result.get(0).getContactEmail());


        //when(contactBookDAO.findByOwner(null, pageable)).

        StatusResponse statusResponse = contactBookService.getContactById("", "1").getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);



        statusResponse = contactBookService.getContactById(null, "1").getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);



        statusResponse = contactBookService.getContactById("chakri", null).getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);


        statusResponse = contactBookService.getContactById("chakri", "").getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);


    }

    @Test
    public void searchContacts() throws Exception{

        List<Contact> contactList = new ArrayList<Contact>();
        contactList.add(new Contact("chakri", "divya", "divya@gmail.com"));

        Pageable pageable = new PageRequest(0, 3);

        when(contactBookDAO.searchContacts("divya", "chakri", pageable)).thenReturn(contactList);

        List<Contact> result = contactBookService.searchContacts("chakri", "divya", pageable).getData();

        System.out.println("result size is :" + result.size());

        assertEquals(1, result.size());

        assertEquals("divya@gmail.com", result.get(0).getContactEmail());


        StatusResponse statusResponse = contactBookService.searchContacts("", "divya", pageable).getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);



        statusResponse = contactBookService.searchContacts(null, "divya", pageable).getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);



        statusResponse = contactBookService.searchContacts("chakri", null, pageable).getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);


        statusResponse = contactBookService.searchContacts("chakri", "", pageable).getStatus();

        assertNotEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.SUCCESS);



        when(contactBookDAO.searchContacts("divya", "chakri", pageable)).thenThrow(new ContactNotFoundException("error searching contacts"));

        ContactResponse contactResponse = contactBookService.searchContacts("chakri", "divya", pageable);

        assertEquals(contactResponse.getStatus().getStatusType().ERROR, StatusResponse.Type.ERROR);



    }

    @Test
    public void deleteContact() throws Exception {


        List<Contact> contactList = new ArrayList<Contact>();

        Contact contact = new Contact("chakri", "divya", "divya@gmail.com");
        contactList.add(contact);

        Pageable pageable = new PageRequest(0, 3);

        when(contactBookDAO.searchContacts("divya", "chakri", pageable)).thenReturn(contactList);

        doNothing().when(contactBookDAO).delete(contact);

        StatusResponse statusResponse = contactBookService.deleteContact("chakri", "divya").getStatus();

        assertEquals(statusResponse.getStatusType().SUCCESS, StatusResponse.Type.SUCCESS);



        statusResponse = contactBookService.deleteContact(null, "divya").getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);



        statusResponse = contactBookService.deleteContact("chakri", null).getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);


        statusResponse = contactBookService.deleteContact("chakri", "").getStatus();

        assertNotEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.SUCCESS);




        when(contactBookDAO.searchContacts("divya", "chakri", pageable)).thenThrow(new ContactNotFoundException("error searching contacts"));

        ContactResponse contactResponse = contactBookService.deleteContact("chakri", "divya");

        assertEquals(contactResponse.getStatus().getStatusType().ERROR, StatusResponse.Type.ERROR);


    }

    @Test
    public void updateContact() throws Exception {


        Contact contact = new Contact("chakri", "divya", "divya@gmail.com");

        Contact updatedContact = new Contact("chakri", "dhanya", "divya@gmail.com");

        Pageable pageable = new PageRequest(0, 3);

        when(contactBookDAO.findById("1")).thenReturn(contact);

        when(contactBookDAO.save(contact)).thenReturn(updatedContact);


        List<Contact> result = contactBookService.updateContact("chakri", "1", contact).getData();

        System.out.println("result size is :" + result.size());

        assertEquals(1, result.size());

        System.out.println("changed ContactName is :"+ result.get(0).getContactName());

        assertEquals("dhanya", result.get(0).getContactName());




        StatusResponse statusResponse = contactBookService.updateContact("", "1", contact).getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);



        statusResponse = contactBookService.updateContact(null, "1",contact).getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);



        statusResponse = contactBookService.updateContact("chakri", null, contact).getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);


        statusResponse = contactBookService.updateContact("chakri", "", contact).getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);



        statusResponse = contactBookService.updateContact("chakri", "1", null).getStatus();

        assertEquals(statusResponse.getStatusType().ERROR, StatusResponse.Type.ERROR);


        when(contactBookDAO.findById("1")).thenThrow(new InvalidContactException("Contact Not Available"));

        ContactResponse contactResponse = contactBookService.updateContact("chakri", "1", contact);

        assertEquals(contactResponse.getStatus().getStatusType().ERROR, StatusResponse.Type.ERROR);



    }

}