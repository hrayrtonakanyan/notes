package com.disqo.assessment.notes.exceptions;

import com.disqo.assessment.notes.models.network.Response;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/16/21.
 * Time: 2:15 AM.
 */
public class InvalidRequestException extends InternalException {

    public InvalidRequestException(Response.ErrorType type, String message) {
        super(type, message);
    }
}
