package co.empresa.productoservice.delivery.rest;


import co.empresa.productoservice.domain.exception.ValidationException;
import co.empresa.productoservice.domain.exception.ProductoNoEncontradoException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String,Object>> handleValidation(ValidationException ex) {
        var errors = ex.getResult().getFieldErrors().stream()
                .map(err -> Map.of("field", err.getField(), "message", err.getDefaultMessage()))
                .collect(Collectors.toList());

        var body = new HashMap<String,Object>();
        body.put("error", "Error de validación");
        body.put("errores", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body); // 400
    }

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<Map<String,Object>> handleNotFound(ProductoNoEncontradoException ex) {
        var body = new HashMap<String,Object>();
        body.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body); // 404
    }
}
