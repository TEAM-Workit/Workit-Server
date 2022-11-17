package workit.util;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ResponseMessage> handleCustomException(Exception e) {
        if (e instanceof CustomException) {
            return ResponseMessage.toResponseEntity(((CustomException) e).getResponseCode());
        } else if (e instanceof MethodArgumentNotValidException || e instanceof HttpMessageNotReadableException) {
            return ResponseMessage.toResponseEntity(new CustomException(ResponseCode.NO_VALUE_REQUIRED).getResponseCode());
        }
        System.out.println(e);
        return ResponseMessage.toResponseEntity(new CustomException(ResponseCode.BAD_REQUEST).getResponseCode());
    }
}
