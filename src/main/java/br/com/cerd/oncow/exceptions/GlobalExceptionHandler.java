package br.com.cerd.oncow.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.com.cerd.oncow.exceptions.ContributionNotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice //"Essa classe vai cuidar das exceções que acontecerem nos meus Controllers."
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)  //"Quando ocorrer um erro de validação do @Valid, execute este método."
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(ContributionNotFoundException.class)
    public ResponseEntity<String> handleContributionNotFound(ContributionNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}

