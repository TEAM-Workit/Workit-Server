package workit.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseNonDataMessage> handle(CustomException e) {
        return new ResponseEntity<>(ResponseNonDataMessage.toResponseEntity(e.getResponseCode()), (e.getResponseCode().getHttpStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseNonDataMessage> handle(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(ResponseNonDataMessage.toResponseEntity(ResponseCode.NULL_VALUE), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handle(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}


