package com.thoughtworks.parking_lot.handler;

import com.thoughtworks.parking_lot.error.CustomError;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.NotSupportedException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ResponseBody
    public CustomError handleNotFoundException(NotFoundException e) {
        CustomError customError = new CustomError();
        customError.setErrorCode(HttpStatus.NOT_FOUND.value());
        customError.setErrorMessage(e.getMessage());

        return customError;
    }

    @ExceptionHandler(NotSupportedException.class)
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public CustomError handleNotSupportedException(NotSupportedException e) {
        CustomError customError = new CustomError();
        customError.setErrorCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        customError.setErrorMessage(e.getMessage());

        return customError;
    }
}
