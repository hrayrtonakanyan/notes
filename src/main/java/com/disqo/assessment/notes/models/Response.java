package com.disqo.assessment.notes.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/15/21.
 * Time: 11:25 PM.
 */
public class Response {

    private boolean error;
    private ErrorType errorType;
    private Object result;

    public Response(ErrorType errorType) {
        this.error = true;
        this.errorType = errorType;
    }

    public Response(Object result) {
        this.result = result;
    }

    public Response(String key, Object value) {
        Map<String, Object> response = new HashMap<>();
        response.put(key, value);
        this.result = response;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Response{" +
                "error=" + error +
                ", errorType=" + errorType +
                ", result=" + result +
                '}';
    }

    public enum ErrorType {
        METHOD_NOT_ALLOWED,
        PATH_PARAMETER_MISSED,
        PARAMETER_MISSED,
        REQUEST_URI_NOT_FOUND,
        PARAMETER_TYPE_MISMATCH,
        INTERNAL_ERROR
    }
}
