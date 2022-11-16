package workit.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
public class ResponseMessage {
    private final int status;
    private final boolean success;
    private final String message;
    private final Object data;

    public static ResponseEntity<ResponseMessage> toResponseEntity(ResponseCode responseCode) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(ResponseMessage.builder()
                        .status(responseCode.getHttpStatus().value())
                        .success(responseCode.getSuccess())
                        .message(responseCode.getMessage())
                        .build()
                );
    }

    public static ResponseEntity<ResponseMessage> toResponseEntity(ResponseCode responseCode, Object data) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(ResponseMessage.builder()
                        .status(responseCode.getHttpStatus().value())
                        .success(responseCode.getSuccess())
                        .message(responseCode.getMessage())
                        .data(data)
                        .build()
                );
    }
}
