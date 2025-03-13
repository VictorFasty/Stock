package victornext.stock.Controller.Commom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import victornext.stock.Exceptions.DuplicatedException;
import victornext.stock.Exceptions.InvalidField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedException.class)
    public ResponseEntity<Map<String, Object>> handlerDuplicatedException(DuplicatedException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", e.getMessage()); // CORRIGIDO: Removido ":" extra

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidField.class)
    public ResponseEntity<Map<String, Object>> handlerInvalidField(InvalidField e) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Invalid field");
        body.put("message", e.getMessage()); // CORRIGIDO: Removido ":" extra

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
