package com.disqo.assessment.notes.handlers;

import com.disqo.assessment.notes.exceptions.InternalException;
import com.disqo.assessment.notes.models.network.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/16/21.
 * Time: 2:36 AM.
 */
@ControllerAdvice
public class NotesResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // region Static fields
    private static final Logger logger = LogManager.getLogger(NotesResponseEntityExceptionHandler.class);
    // endregion

    // region Business logic
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleConflict(Exception e, WebRequest request) {
        Response.ErrorType type = Response.ErrorType.INTERNAL_ERROR;
        if (e instanceof InternalException) {
            type = ((InternalException) e).getType();
        }
        return handleException(e, request, type);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(e, request, Response.ErrorType.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(e, request, Response.ErrorType.PATH_PARAMETER_MISSED);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(e, request, Response.ErrorType.PARAMETER_MISSED);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(e, request, Response.ErrorType.REQUEST_URI_NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(e, request, Response.ErrorType.PARAMETER_TYPE_MISMATCH);
    }

    private ResponseEntity<Object> handleException(Exception e, WebRequest request, Response.ErrorType type) {
        String uri = ((ServletWebRequest) request).getRequest().getServletPath();
        logger.error("[" + type + "] [" + uri + "] " + e.getMessage());
        logger.info("[" + type + "] [" + uri + "] " + e.getMessage(), e);
        return handleExceptionInternal(e, new Response(type), null, HttpStatus.OK, request);
    }
    // endregion
}
