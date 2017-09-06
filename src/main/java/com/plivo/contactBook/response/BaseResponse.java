package com.plivo.contactBook.response;


/**
 * Created by m01457 on 04/09/17.
 */

import java.io.Serializable;




/**
 * The Class AbstractResponse.
 */
public abstract class BaseResponse implements Serializable {

    /** The status. */
    private StatusResponse status;

    /**
     * Instantiates a new base response.
     */
    public BaseResponse() {
    }

    /**
     * Instantiates a new base response.
     *
     * @param status the status
     */
    public BaseResponse(StatusResponse status) {
        this.status = status;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public StatusResponse getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(StatusResponse status) {
        this.status = status;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BaseResponse [status=" + status + "]";
    }
}


