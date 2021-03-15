package com.revature.gameStart.util;

import com.revature.gameStart.exceptions.InvalidRequestException;
import com.revature.gameStart.exceptions.ResourceNotFoundException;
import com.revature.gameStart.exceptions.ResourcePersistenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class, InvalidRequestException.class,
            ResourceNotFoundException.class, ResourcePersistenceException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {



        String bodyOfResponse = ChooseResponseBody(ex);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    String ChooseResponseBody(RuntimeException ex) {
        if (ex.equals(IllegalArgumentException.class)) {
            return "Illegal Argument";
        }
        else if (ex.equals(IllegalStateException.class)) {
            return "Illegal State";
        }
        else if (ex.equals(InvalidRequestException.class)) {
            return "Invalid Request";
        }
        else if (ex.equals(ResourceNotFoundException.class)) {
            return "Resource Not Found";
        }
        else if (ex.equals(ResourcePersistenceException.class)) {
            return "Resource Persistence Exception";
        }

        return "An Exception occurred.";

    }
}
