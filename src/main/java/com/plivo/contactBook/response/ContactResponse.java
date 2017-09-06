package com.plivo.contactBook.response;

import com.plivo.contactBook.domain.Contact;

import java.util.List;

/**
 * Created by m01457 on 04/09/17.
 */
public class ContactResponse extends BaseResponse{

    /**
     * Instantiates a new contact response.
     */
    public ContactResponse() {
        super();
    }

    /**
     * Instantiates a new contact response.
     *
     * @param statusResponse the status response
     */
    public ContactResponse(StatusResponse statusResponse) {
        super(statusResponse);
    }

    /** The data. */
    private List<Contact> data;

    /**
     * Gets the data.
     *
     * @return the data
     */
    public List<Contact> getData() {
        return data;
    }

    /**
     * Sets the data.
     *
     * @param data the new data
     */
    public void setData(List<Contact> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "ContactResponse{" + "data:" + data + '}';
    }


}
