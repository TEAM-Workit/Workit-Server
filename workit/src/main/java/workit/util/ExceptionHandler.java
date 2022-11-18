package workit.util;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ResponseNonDataMessage> handleCustomException(Exception e) {
        if (e instanceof CustomException) {
            return ResponseNonDataMessage.toResponseEntity(((CustomException) e).getResponseCode());
        } else if (e instanceof MethodArgumentNotValidException || e instanceof HttpMessageNotReadableException) {
            return ResponseNonDataMessage.toResponseEntity(new CustomException(ResponseCode.NO_VALUE_REQUIRED).getResponseCode());
        }
        System.out.println(e);
        return ResponseNonDataMessage.toResponseEntity(new CustomException(ResponseCode.BAD_REQUEST).getResponseCode());
    }
}
