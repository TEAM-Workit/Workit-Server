package workit.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
public class ResponseNonDataMessage {
    private final int status;
    private final boolean success;
    private final String message;

    public static ResponseEntity<ResponseNonDataMessage> toResponseEntity(ResponseCode responseCode) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(ResponseNonDataMessage.builder()
                        .status(responseCode.getHttpStatus().value())
                        .success(responseCode.getSuccess())
                        .message(responseCode.getMessage())
                        .build()
                );
    }
}
