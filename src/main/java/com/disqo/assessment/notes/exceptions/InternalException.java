package com.disqo.assessment.notes.exceptions;

import com.disqo.assessment.notes.models.network.Response;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/16/21.
 * Time: 2:16 AM.
 */
public class InternalException extends RuntimeException {

    private final Response.ErrorType type;

    public InternalException(Response.ErrorType type, String message) {
        super(message);
        this.type = type;
    }

    public InternalException(Response.ErrorType type, String message, Throwable th) {
        super(message, th);
        this.type = type;
    }

    public Response.ErrorType getType() {
        return type;
    }
}
