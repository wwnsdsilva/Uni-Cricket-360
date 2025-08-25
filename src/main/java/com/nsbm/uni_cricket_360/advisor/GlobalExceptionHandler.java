package com.nsbm.uni_cricket_360.advisor;

import com.nsbm.uni_cricket_360.exception.*;
import com.nsbm.uni_cricket_360.util.ResponseUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.stream.Collectors;

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
    public ResponseUtil handleNotFoundExceptions(NotFoundException ex) {
        return new ResponseUtil(404, ex.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(InvalidRoleException.class)
    public ResponseUtil handleInvalidRoleExceptions(InvalidRoleException ex) {
        return new ResponseUtil(406, ex.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ImageFileException.class})
    public ResponseUtil handleImageFileException(Exception ex) {
        return new ResponseUtil(500, ex.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseUtil handleInvalidEnum(HttpMessageNotReadableException ex) {
//        String message = "Invalid value provided. Please use one of: BATSMAN, BOWLER, ALLROUNDER, WICKETKEEPER";
//        return new ResponseUtil(400, message, null);

        Throwable cause = ex.getCause();
        if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException) {
            com.fasterxml.jackson.databind.exc.InvalidFormatException ife =
                    (com.fasterxml.jackson.databind.exc.InvalidFormatException) cause;

            Class<?> targetType = ife.getTargetType();
            if (targetType.isEnum()) {
                // Collect all possible enum values
                String allowedValues = Arrays.stream(targetType.getEnumConstants())
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));

                String message = String.format(
                        "Invalid value '%s' provided for %s. Allowed values are: %s",
                        ife.getValue(),
                        targetType.getSimpleName(),
                        allowedValues
                );

                return new ResponseUtil(400, message, null);
            }
        }

        // fallback for other cases
        return new ResponseUtil(400, "Invalid request body format", null);
    }

    // Handles validation errors for @Valid / @Validated objects
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseUtil handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Get the first error message (you can also collect all errors if needed)
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining("; "));  // combine multiple messages

        return new ResponseUtil(400, message, null);
    }

    // Handles validation errors for request params / path variables
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseUtil handleConstraintViolation(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations()
                .stream()
                .map(cv -> cv.getMessage())
                .collect(Collectors.joining("; "));

        return new ResponseUtil(400, message, null);
    }
}
