package workit.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseMessage {
    private final int status;
    private final boolean success;
    private final String message;
    private Object data;

    public static ResponseMessage toResponseEntity(ResponseCode responseCode) {
        return ResponseMessage
                .builder()
                .status(responseCode.getStatusCode().getValue())
                .success(responseCode.getSuccess())
                .message(responseCode.getMessage())
                .build();
    }

    public static ResponseMessage toResponseEntity(ResponseCode responseCode, Object data) {
        return ResponseMessage
                .builder()
                .status(responseCode.getStatusCode().getValue())
                .success(responseCode.getSuccess())
                .message(responseCode.getMessage())
                .data(data)
                .build();
    }
}
