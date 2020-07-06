package ru.ase.ims.enomanager.controller.exceptionhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.ase.ims.enomanager.exception.CreateReleaseException;


@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler({CreateReleaseException.class})
    protected ResponseEntity<Object> handleBranchCreationException(CreateReleaseException ex) {
        return ResponseEntity.badRequest().body(ex);
    }
}
