package com.plivo.contactBook.response;

import java.io.Serializable;

/**
 * Created by m01457 on 04/09/17.
 */



import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The Class StatusResponse.
 */
@XmlRootElement(name = "status")
public class StatusResponse implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5521850192852967435L;

    /** The status type. */
    private Type statusType;



    /** The status message */
    private String message;


    /**
     * The Enum Type.
     */
    public static enum Type {

        /** The error. */
        ERROR,
        /** The success. */
        SUCCESS,
        /** The warning. */
        WARNING;
    };

    /**
     * Instantiates a new status response.
     */
    public StatusResponse() {
    }

    /**
     * Instantiates a new status response.
     *
     *
     *
     * @param type the type
     */
    public StatusResponse(StatusResponse.Type type) {

        this.statusType = type;
    }

    public StatusResponse(StatusResponse.Type type,String message) {

        this.statusType = type;
        this.message = message;
    }


    /**
     * Gets the status type.
     *
     * @return the status type
     */
    public Type getStatusType() {
        return statusType;
    }

    /**
     * Sets the status type.
     *
     * @param statusType the new status type
     */
    public void setStatusType(Type statusType) {
        this.statusType = statusType;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "StatusResponse [statusType=" +
                statusType +
                "message=" + message
                + "]";
    }
}


