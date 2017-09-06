package com.plivo.contactBook.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.math.BigInteger;

/**
 * Created by m01457 on 02/09/17.
 */
@Document(collection = "contacts")
@CompoundIndexes({
        @CompoundIndex(name = "contact_unqiue_index",
                unique = true,
                def = "{'owner' : 1, 'contactEmail' : 1}")
})
public class Contact {

    @Id
    private String id;

    private String owner;

    private String contactName;

    private String contactEmail;

    public Contact() {}


    public Contact(String owner, String contactName, String contactEmail) {
        this.owner = owner;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }


    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                '}';
    }
}
