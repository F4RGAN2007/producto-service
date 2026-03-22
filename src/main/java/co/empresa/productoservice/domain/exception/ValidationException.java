package co.empresa.productoservice.domain.exception;

import org.springframework.validation.BindingResult;

public class ValidationException extends RuntimeException {
    private final BindingResult result;
    public ValidationException(BindingResult result) {
        super("Error de validación");
        this.result = result;
    }
    public BindingResult getResult() { return result; }
}
