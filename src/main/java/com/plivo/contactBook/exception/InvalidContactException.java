package com.plivo.contactBook.exception;

/**
 * Created by m01457 on 03/09/17.
 */
public class InvalidContactException extends  Exception{

    public InvalidContactException(){}

    public InvalidContactException(String message)
    {
        super(message);
    }

}
