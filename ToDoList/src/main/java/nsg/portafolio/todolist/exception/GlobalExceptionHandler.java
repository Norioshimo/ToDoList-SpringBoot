package nsg.portafolio.todolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nsg.portafolio.todolist.dto.ResponseWrapper;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja errores de validación con @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Mensaje general
        String generalMessage = "Se encontraron errores en la solicitud";

        // Lista de errores detallados
        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> {
                    Map<String, String> errorDetail = new HashMap<>();
                    errorDetail.put("campo", error.getField());
                    errorDetail.put("mensaje", error.getDefaultMessage());
                    return errorDetail;
                })
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(new ResponseWrapper<List<Map<String, String>>>(errors, generalMessage));
    }

    // Maneja excepciones generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseWrapper<String>("Ocurrió un error inesperado: " + ex.getMessage()));
    }
}
