package workit.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseNonDataMessage {
    private final int status;
    private final boolean success;
    private final String message;

    public static ResponseNonDataMessage toResponseEntity(ResponseCode responseCode) {
        return ResponseNonDataMessage
                .builder()
                .status(responseCode.getStatusCode().getValue())
                .success(responseCode.getSuccess())
                .message(responseCode.getMessage())
                .build();
    }
}
