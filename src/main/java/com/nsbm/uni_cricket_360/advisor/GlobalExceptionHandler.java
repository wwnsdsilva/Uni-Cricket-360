package com.nsbm.uni_cricket_360.advisor;

import com.nsbm.uni_cricket_360.exception.*;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseUtil handleInvalidCredentials(InvalidCredentialsException ex) {
        return new ResponseUtil(401, ex.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseUtil handleExpiredTokens(ExpiredTokenException ex) {
        return new ResponseUtil(401, ex.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseUtil handleExpiredTokens(NotFoundException ex) {
        return new ResponseUtil(404, ex.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(InvalidRoleException.class)
    public ResponseUtil handleExpiredTokens(InvalidRoleException ex) {
        return new ResponseUtil(500, ex.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ImageFileException.class})
    public ResponseUtil handleImageFileException(Exception ex) {
        return new ResponseUtil(500, ex.getMessage(), null);
    }
}
