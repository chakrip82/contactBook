package com.plivo.contactBook.exception;

/**
 * Created by m01457 on 03/09/17.
 */
public class ContactNotFoundException extends Exception{

    public ContactNotFoundException(){}

    public ContactNotFoundException(String message)
    {
        super(message);
    }
}
