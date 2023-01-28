package workit.util;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    protected ResponseNonDataMessage handleCustomException(Exception e) {
        if (e instanceof CustomException) {
            return ResponseNonDataMessage.toResponseEntity(((CustomException) e).getResponseCode());
        } else if (e instanceof MethodArgumentNotValidException || e instanceof HttpMessageNotReadableException) {
            return ResponseNonDataMessage.toResponseEntity(new CustomException(ResponseCode.NULL_VALUE).getResponseCode());
        }
        System.out.println(e);
        return ResponseNonDataMessage.toResponseEntity(new CustomException(ResponseCode.BAD_REQUEST).getResponseCode());
    }
}


